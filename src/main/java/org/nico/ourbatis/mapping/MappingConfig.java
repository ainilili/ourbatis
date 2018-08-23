package org.nico.ourbatis.mapping;

import java.util.HashMap;
import java.util.Map;

public class MappingConfig {

	public static final Map<Class<?>, String> JAVA_TYPE_MAPPING_MYSQL = new HashMap<Class<?>, String>(){
		private static final long serialVersionUID = 3388503832916481034L;
	{
		put(String.class, "VARCHAR");
		put(Integer.class, "INT");
	}};
}
