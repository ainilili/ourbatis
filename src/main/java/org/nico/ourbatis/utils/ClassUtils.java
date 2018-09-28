package org.nico.ourbatis.utils;

import java.io.IOException;
import java.util.Set;

import org.apache.ibatis.io.ResolverUtil;

public class ClassUtils {
	
	public static Class<?> forName(String name){
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public static Set<Class<? extends Class<?>>> getClasses(String pack) throws IOException {
		ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
	    resolverUtil.find(new ResolverUtil.IsA(Object.class), pack);
	    return resolverUtil.getClasses();
	}
}
