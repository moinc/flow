/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.jarinspector;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;

public class JarInspector implements Closeable {
	private static final Logger LOG = Logger.getLogger(JarInspector.class.getName());

	public static JarInspector loadFrom(File file, InspectionObserver inspectionObserver) {
		return new JarInspector(file, inspectionObserver);
	}

	final File file;
	final InspectionObserver inspectionObserver;
	boolean loaded;
	ClassLoader loader;
	final List<String> entries = new ArrayList<String>();
	final List<Class<?>> classes = new ArrayList<>();

	private JarInspector(File file, InspectionObserver inspectionObserver) {
		Assertions.notNull(file, "file");
		Assertions.exists(file, "file");
		Assertions.notNull(inspectionObserver, "inpectionObserver");
		this.file = file;
		this.inspectionObserver = inspectionObserver;
	}

	private void loadEntries() throws IOException {
		this.entries.clear();
		try (JarFile jarFile = new JarFile(file)) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				String name = entries.nextElement().getName();
				LOG.fine("found entry: " + name);
				this.entries.add(name);
			}
		}
	}

	private String getClassNameFromEntry(String entry) {
		Assertions.notEmpty(entry, "entry");
		return entry.substring(0, entry.length() - ".class".length()).replace("/", ".");
	}

	private Class<?> getClassForName(String className) throws ClassNotFoundException {
		Assertions.notEmpty(className, "className");
		return Class.forName(className, true, loader);
	}

	public void inspect() throws InspectorLoadException {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.normal().append("inspecting project: " + file.getAbsolutePath()).print();
			if (loaded) {
				return;
			}
			try {
				loadEntries();
				if (entries.size() == 0) {
					throw new InspectorLoadException("specified file is empty");
				}
				URL url = file.toURI().toURL();
				this.loader = URLClassLoader.newInstance(new URL[] { url }, getClass().getClassLoader());
				inspectClasses(getClasses());
			} catch (MalformedURLException e) {
				throw new InspectorLoadException(e);
			} catch (IOException e) {
				throw new InspectorLoadException(e);
			}
			loaded = true;
		}
	}

	public List<String> getEntries(EntryType type) {
		Assertions.notNull(type, "type");
		List<String> entries = new ArrayList<>();
		for (String entry : this.entries) {
			if (entry.endsWith(".class")) {
				// class file
				if (type == EntryType.CLASS) {
					entries.add(entry);
				}
			} else if (entry.endsWith("/")) {
				// package/folder
				if (type == EntryType.PACKAGE) {
					entries.add(entry);
				}
			} else {
				// resource
				if (type == EntryType.RESOURCE) {
					entries.add(entry);
				}
			}
		}
		return entries;
	}

	private List<Class<?>> getClasses() throws InspectorLoadException {
		if (classes.size() != 0) {
			return classes;
		}
		List<String> entries = getEntries(EntryType.CLASS);
		LOG.fine("found: " + entries.size() + " class entries");
		for (String entry : entries) {
			String className = getClassNameFromEntry(entry);
			try {
				Class<?> clazz = getClassForName(className);
				LOG.fine("initialized class: " + clazz.getName());
				classes.add(clazz);
			} catch (ClassNotFoundException e) {
				throw new InspectorLoadException("failed to initialize class: " + className, e);
			}
		}
		return classes;
	}

	private void inspectClasses(List<Class<?>> classes) {
		Assertions.notNull(classes, "classes");
		LOG.fine("inspecting classes...");
		for (Class<?> clazz : classes) {
			inspectionObserver.observe(clazz);
		}
	}

	@Override
	public void close() throws IOException {
		if (loaded) {
			classes.clear();
			loader = null;
			loaded = false;
		}
	}
}
