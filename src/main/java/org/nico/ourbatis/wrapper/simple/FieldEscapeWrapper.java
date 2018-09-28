package org.nico.ourbatis.wrapper.simple;

import org.nico.ourbatis.wrapper.Wrapper;

/**
 * Escapes database fields with ‘`’ around them
 * 
 * @author nico
 */
public class FieldEscapeWrapper implements Wrapper<String>{

	private final static String ESCAPE = "`";
	
	@Override
	public String wrapping(String value) {
		if(! value.startsWith(ESCAPE)) {
			value = ESCAPE + value;
		}
		if(! value.endsWith(ESCAPE)) {
			value = value + ESCAPE;
		}
		return value;
	}
}
