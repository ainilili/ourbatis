package org.nico.ourbatis.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConvertUtils {

	public static Map<String, Object> collectionToMap(Collection<?> collection){
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 0;
		for(Object o: collection) {
			map.put(String.valueOf(index ++), o);
		}
		return map;
	}
}
