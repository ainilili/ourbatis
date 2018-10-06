package org.nico.ourbatis.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
	
	public static Class<?> forName(String name){
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public static List<Class<?>> getClasses(String pack) throws IOException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		boolean recursive = true;
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		dirs = Thread.currentThread().getContextClassLoader().getResources(
				packageDirName);
		while (dirs.hasMoreElements()) {
			URL url = dirs.nextElement();
			String protocol = url.getProtocol();
			if ("file".equals(protocol)) {
				String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
				findAndAddClassesInPackageByFile(packageName, filePath,
						recursive, classes);
			} else if ("jar".equals(protocol)) {
				JarFile jar;
				try {
					jar = ((JarURLConnection) url.openConnection())
							.getJarFile();
					Enumeration<JarEntry> entries = jar.entries();
					while (entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						if (name.charAt(0) == '/') {
							name = name.substring(1);
						}
						if (name.startsWith(packageDirName)) {
							int idx = name.lastIndexOf('/');
							if (idx != -1) {
								packageName = name.substring(0, idx)
										.replace('/', '.');
							}
							if ((idx != -1) || recursive) {
								if (name.endsWith(".class")
										&& !entry.isDirectory()) {
									String className = name.substring(
											packageName.length() + 1, name
											.length() - 6);
									try {
										classes.add(Class
												.forName(packageName + '.'
														+ className));
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return classes;
	}
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, List<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return (recursive && dir.isDirectory())
						|| (name.endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));  
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
