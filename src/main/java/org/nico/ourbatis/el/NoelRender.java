package org.nico.ourbatis.el;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoelRender {

	private String suffix;
	
	private String prefix;
	
	public NoelRender(String suffix, String prefix) {
		this.suffix = suffix;
		this.prefix = prefix;
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
				if(currentChar == '}') {
					results.add(result.toString() + "}");
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
	
	public String rending(Object o, String render, String elem) {
		elem = prefix + elem;
		String[] segments = splitRender(render, elem);
		
		StringBuilder result = new StringBuilder();
		for(int index = 0; index < segments.length; index ++) {
			String segment = segments[index];
			if(segment.startsWith(elem + '.') || segment.startsWith(elem + '}')) {
				int indexof = segment.indexOf(".");
				if(indexof != -1) {
					segments[index] = getValueWrapper(getFinalValue(segment.replaceAll("[\\@|\\{|\\}]", ""), o));
				}else {
					segments[index] = getValueWrapper(getValue(o, elem.replaceAll("[\\@|\\{|\\}]", "")));
				}
			}
			result.append(segments[index]);
		}
		return result.toString();
		
	}
	
	/**
	 * 空对象处理
	 * 
	 * @param value
	 * @return
	 */
	public String getValueWrapper(Object value) {
		return value == null ? "":value.toString();
	}
	
	/**
	 * 获取Field的值
	 * 
	 * @param fieldName
	 * @param obj
	 * @return
	 */
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
	
	/**
	 * 获取Class的Field
	 * 
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
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
	
	/**
	 * 获取el表达式对应的值
	 * 
	 * @param fieldNames
	 * @param obj
	 * @return
	 */
	public Object getFinalValue(String fieldNames, Object obj) {
		String[] fieldSegs = fieldNames.split("[.]");
		Object target = obj;
		for(int index = 1; index < fieldSegs.length; index ++) {
			String segs = fieldSegs[index];
			if(target != null) {
				target = getValue(target, segs);
			}
		}
		return target;
	}
	
	/**
	 * 获取对象中的值
	 * 支持对象和Map
	 * 
 	 * @param obj 
 	 * 			要获取值的对象
	 * @param key 
	 * 			要获取的值得名字
	 * @return
	 * 			目标
	 */
	public Object getValue(Object obj, String key) {
		Object target = "";
		if(obj instanceof Map) {
			target = ((Map)obj).get(key);
		}else {
			return getFieldValue(key, obj);
		}
		return target == null ? "" : target.toString();
	}
}
