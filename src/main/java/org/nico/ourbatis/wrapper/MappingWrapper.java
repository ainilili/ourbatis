package org.nico.ourbatis.wrapper;

import java.util.ArrayList;
import java.util.List;

public class MappingWrapper {

	public static final List<Wrapper> FIELD_WRAPPERS = new ArrayList<Wrapper>();
	
	public static final List<Wrapper> CLASS_WRAPPERS = new ArrayList<Wrapper>();
	
	public static interface Wrapper{
		public String wrapper(String value);
	}
	
	public static String wrappingFieldWrappers(String value) {
		return wrapping(value, FIELD_WRAPPERS);
	}
	
	public static String wrappingClassWrappers(String value) {
		return wrapping(value, CLASS_WRAPPERS);
	}
	
	public static String wrapping(String value, List<Wrapper> wrappers) {
		if(wrappers == null) {
			throw new NullPointerException("Wrappers is null");
		}
		String afterWrapper = value;
		for(Wrapper wrapper: wrappers) {
			afterWrapper = wrapper.wrapper(afterWrapper);
		}
		return afterWrapper;
	}
}
