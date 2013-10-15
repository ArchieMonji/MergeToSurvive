package com.vgdc.merge.assets;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassFinder {
	/**
	 * Code from: http://dzone.com/snippets/get-all-classes-within-package
	 * 
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * Adapted from http://snippets.dzone.com/posts/show/4831 and extended to
	 * support use of JAR files
	 * 
	 * @param packageName
	 *            The base package
	 * @param regexFilter
	 *            an optional class name pattern.
	 * @return The classes
	 */
	public static ArrayList<Class<?>> getClassesInPackage(String packageName) {
		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			assert classLoader != null;
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = classLoader.getResources(path);
			List<String> dirs = new ArrayList<String>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(resource.getFile());
			}
			ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
			for (String directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
			return classes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Code from: http://dzone.com/snippets/get-all-classes-within-package
	 * 
	 * Recursive method used to find all classes in a given path (directory or
	 * zip file url). Directories are searched recursively. (zip files are
	 * Adapted from http://snippets.dzone.com/posts/show/4831 and extended to
	 * support use of JAR files
	 * 
	 * @param path
	 *            The base directory or url from which to search.
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @param regex
	 *            an optional class name pattern. e.g. .*Test
	 * @return The classes
	 */
	private static ArrayList<Class<?>> findClasses(String path, String packageName)
			throws Exception {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		if (path.startsWith("file:") && path.contains("!")) {
			String[] split = path.split("!");
			URL jar = new URL(split[0]);
			ZipInputStream zip = new ZipInputStream(jar.openStream());
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if (entry.getName().endsWith(".class")) {
					String className = entry.getName().replaceAll("[$].*", "")
							.replaceAll("[.]class", "").replace('/', '.');
					if (className.startsWith(packageName)){
						classes.add(Class.forName(className));
					}
				}
			}
			zip.close();
		}
		File dir = new File(path);
		if (!dir.exists()) {
			return classes;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// another package level
				classes.addAll(findClasses(file.getAbsolutePath(), packageName
						+ "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				String className = packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6);
				classes.add(Class.forName(className));
			}
		}
		return classes;
	}
}
