package org.nico.ourbatis.el;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.nico.noson.Noson;
import org.nico.noson.util.string.StringUtils;

/**
 * <p>表达式解析工具，将传入的对象渲染在存在语法的文本部位，让文本变得可以自定义。
 * <p>通过一个Map对象传递参数
 * <p>通过解析语法以达到遍历数据的目的：
 * <p><b>1、不需要遍历的参数，只需要 [$参数名.字段名] [$参数名] 即可</b>
 * <p><b>1、需要遍历的参数，只需要 [$elem.字段名] 即可</b>
 * 在解析出错的时候 ~ 可以代替 '
 * 
 * @author nico
 * @time 2018-06-20 09:23
 */
public class MapperEL {
	
	/**
	 * 匹配前缀
	 */
	private String prefix;
	
	/**
	 * 匹配后缀
	 */
	private String suffix;
	
	/**
	 * 实例化
	 * 
	 * @param prefix 
	 * 			前缀
	 * @param suffix 
	 * 			后缀
	 */
	public MapperEL(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	/**
	 * 渲染入口，使用一个Map对象传参，解析text中的expression
	 * 
	 * @param datas 
	 * 			Map传递参数
	 * @param text 
	 * 			要渲染的文本
	 * @return 
	 * 			渲染后的文本
	 */
	public String render(Map<String, Object> datas, String text) {
		if(StringUtils.isBlank(text)) {
			return "";
		}
		
		if(datas == null) {
			return text;
		}
		
		char[] textChars = text.toCharArray();
		
		int headNode = 0;
		int tailNode = 0;
		
		int length = textChars.length;
		
		//渲染循环语法
		for(int index = 0; index < length; index ++) {
			headNode = text.indexOf(prefix, index);
			if(headNode != -1) {
				index = headNode;
				tailNode = text.indexOf(suffix, index);
				if(tailNode != -1) {
					String parserResult = parser(datas, text.substring(headNode + prefix.length(), tailNode));
					index += parserResult.length();
					length += parserResult.length() - (tailNode - headNode);
					text = text.substring(0, headNode) + parserResult + text.substring(tailNode + suffix.length());
				}
			}else {
				break;
			}
		}
		
		//循环单个参数
		for(Entry<String, Object> entry: datas.entrySet()) {
			text = rending(datas, text, prefix + entry.getKey());
		}
		
		return text;
	}
	
	/**
	 * 语法解析
	 * 
	 * @param datas 
	 * 			Map传递参数
	 * @param json 
	 * 			语法json表示串
	 * @return 
	 * 			解析后的文本
	 */
	public String parser(Map<String, Object> datas, String json) {
		json = "{" + json + "}";
		
		Exer exer = null;
		try {
			exer = Noson.convert(json, Exer.class);
		}catch(StringIndexOutOfBoundsException e) {
			json = json.replaceAll("~", "'");
			exer = Noson.convert(json, Exer.class);
		}
		
		String render = exer.getRender();
		Object obj = datas.get(exer.datas);
		
		StringBuilder result = new StringBuilder();
		if(exer.type.equalsIgnoreCase("foreach")) {
			if(! (obj instanceof Collection)) {
				throw new RuntimeException("Type is [foreach] but data type is not collection");
			}
			Collection<Object> list = (Collection<Object>)obj;
			String elem = exer.getElem();
			int index = 0;
			for(Object o: list) {
				result.append(rending(o, render, prefix + elem));
				if(StringUtils.isNotBlank(exer.getSplit()) && index < list.size() - 1) {
					result.append(exer.getSplit());
				}
				index ++;
			}
		}
		
		return result.toString();
	}
	
	/**
	 * 开始渲染
	 * 
	 * @param o	
	 * 			要渲染的对象
	 * @param render 
	 * 			渲染文本
	 * @param elem 
	 * 			匹配头
	 * @return 
	 * 			渲染好的文本
	 */
	public String rending(Object o, String render, String elem) {
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
	 * <p>将el表达式解析成分块数组
	 * <p>例如 $elem.ab.cd.ef 》 [$elem,ab,cd,ef]
	 * 
	 * @param fields el表达式
	 * @param elem 匹配头
	 * @return 分块数组
	 */
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
	 * 承载语法的实体
	 * 
	 * @author nico
	 * @time 2018-06-22 14:33
	 */
	public static class Exer{
		
		private String datas;
		
		private String type;
		
		private String render;
		
		private String split;
		
		private String elem = "elem";
		
		public String getSplit() {
			return split;
		}

		public void setSplit(String split) {
			this.split = split;
		}

		public String getElem() {
			return elem;
		}

		public void setElem(String elem) {
			this.elem = elem;
		}

		public String getDatas() {
			return datas;
		}

		public void setDatas(String datas) {
			this.datas = datas;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getRender() {
			return render;
		}

		public void setRender(String render) {
			this.render = render;
		}
		
	}
	
	public static void main(String[] args) {
		
		String str = "@{abc} + @{abcd}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("abc", "A");
		map.put("abcd", "a");
		MapperEL eh = new MapperEL("@{", "}#");
		System.out.println(eh.render(map, str));
		
	}
}
