package com.vgdc.merge.assets;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Code adapted from: http://dzone.com/snippets/get-all-classes-within-package
 */
public class ClassFinder {
	public static ArrayList<Class<?>> getClassesInPackage(String packageName) {

		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		URL resource = classLoader.getResource(path);
		String absolutePath = resource.getPath();
		try {
			classes.addAll(findClasses(absolutePath, packageName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	private static ArrayList<Class<?>> findClasses(String path,
			String packageName) throws Exception {
		if (isContainedInJar(path)) {
			return findClassesJar(path, packageName);
		} else {
			return findClassesDesktop(path, packageName);
		}
	}

	private static boolean isContainedInJar(String path) {
		return path.startsWith("file:") && path.contains("!");
	}

	private static ArrayList<Class<?>> findClassesJar(String path,
			String packageName) throws Exception {
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
					if (className.startsWith(packageName)) {
						classes.add(Class.forName(className));
					}
				}
			}
			zip.close();
		}
		return classes;
	}

	private static ArrayList<Class<?>> findClassesDesktop(String path,
			String packageName) throws Exception {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		File dir = new File(path);
		if (!dir.exists()) {
			return classes;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// another package level
				classes.addAll(findClassesDesktop(file.getAbsolutePath(), packageName
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
