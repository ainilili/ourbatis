package org.nico.ourbatis.utils;

public class StringUtils {

	public static final String BLANK = "";
	
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
}
