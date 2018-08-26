package org.nico.ourbatis.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrapperConfig {

	public static final Map<Class<?>, String> JAVA_TYPE_MAPPER = new HashMap<Class<?>, String>(){
		private static final long serialVersionUID = 3388503832916481034L;
	{
		put(String.class, "VARCHAR");
		put(Integer.class, "INT");
	}};
	
	public static final List<Wrapper<String>> JDBC_NAME_WRAPPERS = new ArrayList<Wrapper<String>>();
	
	public static final List<Wrapper<String>> TABLE_NAME_WRAPPERS = new ArrayList<Wrapper<String>>();
	
	public static final List<Wrapper<String>> MAPPER_NAME_WRAPPERS = new ArrayList<Wrapper<String>>();
}
