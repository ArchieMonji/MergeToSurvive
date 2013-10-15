package com.vgdc.merge.assets;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

public class ClassFinder {
	/**
	 * Code adapted from:
	 * http://dzone.com/snippets/get-all-classes-within-package
	 */
	public static ArrayList<Class<?>> getClassesInPackage(String packageName) {
		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
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

	private static ArrayList<Class<?>> findClasses(String path,
			String packageName) {
		if (path.startsWith("file:") && path.contains("!")) {
			try {
				return findClassesJar(path, packageName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				return findClassesDesktop(path, packageName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Code adapted from:
	 * http://dzone.com/snippets/get-all-classes-within-package
	 */
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
				classes.addAll(findClasses(file.getAbsolutePath(), packageName
						+ "." + file.getName()));
			} else {
				String fileName = file.getName();
				System.out.println("F: " + fileName);
				if (fileName.endsWith(".class")) {
					String className = packageName + '.'
							+ fileName.substring(0, fileName.length() - 6);
					classes.add(Class.forName(className));
				}
			}
		}
		return classes;
	}

	/**
	 * Code adapted from:
	 * http://dzone.com/snippets/get-all-classes-within-package
	 */
	private static ArrayList<Class<?>> findClassesJar(String path,
			String packageName) throws Exception {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		if (path.startsWith("file:") && path.contains("!")) {
			String[] split = path.split("!");
			URL jar = new URL(split[0]);
			ZipInputStream zip = new ZipInputStream(jar.openStream());
			for (ZipEntry entry = null; entry != null; entry = zip
					.getNextEntry()) {
				if (entry.getName().endsWith(".class")) {
					String className = entry.getName().replaceAll("[$].*", "")
							.replaceAll("[.]class", "").replace('/', '.');
					if (className.startsWith(packageName))
						classes.add(Class.forName(className));
				}
			}

			zip.close();
		}

		return classes;
	}
}
