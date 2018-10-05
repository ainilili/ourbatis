package org.nico.ourbatis.el;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.nico.ourbatis.utils.ReflactUtils;

/**
 * Template rendering tool
 * 
 * @author nico
 */
public class NoelRender {

	/**
	 * The prefix of the expression container，example '}'
	 */
	private String prefix;
	
	/**
	 * The suffix for the expression container, example '${'
	 */
	private String suffix;

	/**
	 * The content separator in the expression container，if split's value is '.', then the content of the container is ${user.name}
	 */
	private String split;

	/**
	 * Empty string
	 */
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
	
	/**
	 * Renders a text and replaces the placeholder with the content of the key under the match object
	 * 
	 * @param o		
	 * 			Matched object
	 * @param text
	 * 			Text to be rendered
	 * @param key
	 * 			Key to match
	 * @return
	 * 			Key render results to be matched
	 */
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
					target = valueWrapper(getChain(o, target));
				}else {
					target = valueWrapper(getValue(o, target));
				}
			}
			result.append(target);
		});
		return result.toString();
	}
	
	/**
	 * The current key to be rendered is used as the segmentation point, the text 
	 * to be rendered is split into an array, and some elements in the array may 
	 * match the key to be rendered.
	 * 
	 * @param text
	 * 			Text to be rendered
	 * @param elem
	 * 			Key to match
	 * @return
	 * 			Array to be processed
	 */
	public String[] splitRender(String text, String elem) {
		char[] renderChars = text.toCharArray();
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
				headNode = text.indexOf(elem, index);
				if(headNode != -1) {
					results.add(text.substring(index, headNode));
					index = headNode + elem.length() - 1;
					result.append(elem);
					finding = true;
				}else {
					results.add(text.substring(index));
					break;
				}
			}
			if(index == renderChars.length - 1) {
				result.setLength(0);
			}
		}
		return results.toArray(new String[] {});

	}
	
	/**
	 * Packaging value
	 * 	
	 * @param value
	 * 			Value to be wrapped
	 * @return
	 * 			Value after packaging
	 */
	public String valueWrapper(Object value) {
		return value == null ? BLANK:value.toString();
	}
	
	/**
	 * Gets the value of a string chain
	 * 
	 * @param obj
	 * 			Chain head object
	 * @param fieldNames
	 * 			The name of the chain
	 * @return
	 * 			Target object
	 */
	public Object getChain(Object obj, String fieldNames) {
		String[] fieldSegs = fieldNames.split("\\" + split);
		Object target = obj;
		for(int index = 0; index < fieldSegs.length; index ++) {
			String segs = fieldSegs[index];
			if(target != null) {
				target = getValue(target, segs);
			}
		}
		return target;
	}
	
	/**
	 * Gets the value of the specified object field
	 * 
	 * @param obj
	 * 			Object
	 * @param key
	 * 			Field name
	 * @return
	 * 			Target object
	 */
	public Object getValue(Object obj, String key) {
		Object target = null;
		if(obj instanceof Map) {
			target = ((Map<?, ?>)obj).get(key);
		}else if(obj instanceof List){
			target = ((List<?>)obj).get(Integer.valueOf(key));
		}else {
			return ReflactUtils.getFieldValue(key, obj);
		}
		return target;
	}
}
