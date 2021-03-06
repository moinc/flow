/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.compiler.builtin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.compiler.CompileException;
import nl.agiletech.flow.cmp.compiler.Compiler;
import nl.agiletech.flow.cmp.jarinspector.InspectorLoadException;
import nl.agiletech.flow.cmp.jarinspector.JarInspector;
import nl.agiletech.flow.cmp.project.ConfigurationResolver;
import nl.agiletech.flow.cmp.project.Configurator;
import nl.agiletech.flow.cmp.project.RequirementChecker;
import nl.agiletech.flow.cmp.project.DependencyResolver;
import nl.agiletech.flow.cmp.project.GlobalConfigurationMapper;
import nl.agiletech.flow.cmp.project.NodeResolver;
import nl.agiletech.flow.cmp.project.PlatformResolver;
import nl.agiletech.flow.cmp.project.ProjectConfiguration;
import nl.agiletech.flow.cmp.project.RootNodeIdentifier;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.common.util.StringUtil;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.NodeId;

public class DefaultCompiler implements Compiler {
	private static final Logger LOG = Logger.getLogger(DefaultCompiler.class.getName());

	public static Compiler createInstance(DefaultCompilerOptions compileOptions) {
		return new DefaultCompiler(compileOptions);
	}

	final DefaultCompilerOptions compileOptions;

	public DefaultCompiler(DefaultCompilerOptions compileOptions) {
		Assertions.notNull(compileOptions, "compileOptions");
		this.compileOptions = compileOptions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.agiletech.flow.cmp.compiler.Compiler#compile()
	 */
	@Override
	public Context compile() throws CompileException {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.faint().append("--- compile ---").print();

			// create object that holds all project specific classes and
			// resources
			ProjectConfiguration projectConfiguration = new ProjectConfiguration();

			try (JarInspector jarInspector = JarInspector.loadFrom(compileOptions.projectFile, projectConfiguration)) {
				// inspect project file
				jarInspector.inspect();

				// create the context
				Context context;
				try {
					context = Context.createInstance(new DefaultContextValidator(projectConfiguration),
							compileOptions.configurationSettings, compileOptions.requestType, compileOptions.nodeData);
				} catch (Exception e) {
					throw new CompileException("failed to create context", e);
				}

				// process global configuration classes
				GlobalConfigurationMapper.createInstance(projectConfiguration).mapConfigurations(context);

				// process configuration (first time)
				Configurator.createInstance(context, projectConfiguration).configure();

				// identify the node
				NodeId nodeId;
				try {
					nodeId = RootNodeIdentifier.createInstance(projectConfiguration).identify(context);
				} catch (Exception e) {
					throw new CompileException("failed to identify node", e);
				}
				if (nodeId.isUnknown()) {
					throw new CompileException("failed to identify node");
				}

				// resolve the node class
				if (!NodeResolver.createInstance(projectConfiguration).resolveAndAssert(context)) {
					throw new CompileException("unknown node: " + nodeId);
				}

				// resolve and apply configuration mappers
				ConfigurationResolver.createInstance(projectConfiguration).mapConfigurations(context);

				// process configuration (second time)
				Configurator.createInstance(context, projectConfiguration).configure();

				// resolve dependencies
				DependencyResolver.createInstance(context, projectConfiguration).resolve();

				// check requirements
				RequirementChecker.createInstance(context, projectConfiguration).satisfy();

				// locate platform
				PlatformResolver.createInstance(context, projectConfiguration).resolve();

				if (!context.isValid()) {
					throw new CompileException(
							"invalid context: " + StringUtil.join(context.getValidationErrors(), ", "));
				}
				return context;

			} catch (IOException e) {
				throw new CompileException(e);
			} catch (InspectorLoadException e) {
				throw new CompileException(e);
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				throw new CompileException(e);
			} catch (NoSuchAlgorithmException e) {
				throw new CompileException(e);
			}
		}
	}
}
