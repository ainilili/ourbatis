package org.nico.ourbatis.el;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NoelRender {

	private String suffix;

	private String prefix;

	private String split;

	private final static String BLANK = "";

	public NoelRender(String prefix, String suffix) {
		this.suffix = suffix;
		this.prefix = prefix;
		this.split = ".";
	}

	public NoelRender(String prefix, String suffix, String split) {
		this.suffix = suffix;
		this.prefix = prefix;
		this.split = split;
	}

	public String rending(Object o, String text, String key) {
		String prefixKey = prefix + key;
		String assemblySplit = prefixKey + split;
		String assmeblySuffix = prefixKey + suffix;

		String[] segments = splitRender(text, prefixKey);
		StringBuilder result = new StringBuilder();

		Arrays.asList(segments).forEach(target -> {
			if(target.startsWith(assemblySplit) || target.startsWith(assmeblySuffix)) {
				target = target.substring(prefix.length(), target.length() - suffix.length());

				int indexof = target.indexOf(split);
				if(indexof != -1) {
					target = getValueWrapper(getChain(o, target));
				}else {
					target = getValueWrapper(getValue(o, target));
				}
			}
			result.append(target);
		});
		return result.toString();
	}

	public String[] splitRender(String el, String elem) {
		char[] renderChars = el.toCharArray();
		StringBuilder result = new StringBuilder();

		List<String> results = new ArrayList<String>();

		int headNode = 0;
		boolean finding = false;
		for(int index = 0; index < renderChars.length; index ++) {
			char currentChar = renderChars[index];
			if(finding) {
				boolean access = true;
				for(int start = 0; start < suffix.length(); start ++){
					if(suffix.charAt(start) != renderChars[start + index]){
						access = false;
						break;
					}
				}
				if(access) {
					results.add(result.toString() + suffix);
					result.setLength(0);
					finding = false;
				}else {
					result.append(currentChar);
				}
			}else {
				headNode = el.indexOf(elem, index);
				if(headNode != -1) {
					results.add(el.substring(index, headNode));
					index = headNode + elem.length() - 1;
					result.append(elem);
					finding = true;
				}else {
					results.add(el.substring(index));
					break;
				}
			}
			if(index == renderChars.length - 1) {
				result.setLength(0);
			}
		}
		return results.toArray(new String[] {});

	}

	public String getValueWrapper(Object value) {
		return value == null ? BLANK:value.toString();
	}

	public Object getFieldValue(String fieldName, Object obj){
		Object target = null;
		Field field = getField(fieldName, obj.getClass());
		if(field != null) {
			try {
				field.setAccessible(true);
				target = field.get(obj);
				//jump
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return target;
	}
	
	public Field getField(String fieldName, Class<?> clazz){
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			if(clazz.getSuperclass() != null){
				field = getField(fieldName, clazz.getSuperclass());
			}else{
				return null;
			}
		}
		return field;
	}

	public Object getChain(Object obj, String fieldNames) {
		String[] fieldSegs = fieldNames.split("\\" + split);
		Object target = obj;
		for(int index = 1; index < fieldSegs.length; index ++) {
			String segs = fieldSegs[index];
			if(target != null) {
				target = getValue(target, segs);
			}
		}
		return getValueWrapper(target);
	}

	public Object getValue(Object obj, String key) {
		Object target = BLANK;
		if(obj instanceof Map) {
			target = ((Map<?, ?>)obj).get(key);
		}else if(obj instanceof List){
			target = ((List<?>)obj).get(Integer.valueOf(key));
		}else {
			return getFieldValue(key, obj);
		}
		return getValueWrapper(target);
	}
}
