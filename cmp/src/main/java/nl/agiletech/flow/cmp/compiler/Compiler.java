/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.compiler;

import nl.agiletech.flow.project.types.Context;

/**
 * Interface for compilers.
 * 
 * @author moincreemers
 *
 */
public interface Compiler {
	/**
	 * Compiles input into a {@link Context} instance.
	 * 
	 * @return Context
	 * @throws CompileException
	 */
	Context compile() throws CompileException;
}