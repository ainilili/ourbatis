package org.nico.ourbatis.xml;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.noson.util.string.StringUtils;

public class DocumentUtils {

	public static Map<String, String> parseParameters(String paramStr) {
		Map<String, String> parameters = new LinkedHashMap<String, String>();
		if(StringUtils.isNotBlank(paramStr));
		char[] domChars = paramStr.toCharArray();
		String[] kv = new String[2];
		StringBuffer cache = new StringBuffer();
		int singleCount = 0;
		int doubleCount = 0;
		boolean scanValue = false;
		for(int index = 0; index < domChars.length; index++){
			char c = domChars[index];
			if(index == domChars.length - 1){
				if(scanValue){
					kv[1] = cache.toString();
					parameters.put(kv[0], kv[1]);
				}
			}else{
				if(doubleCount == 0 && c == '\''){
					singleCount = singleCount == 1 ? 0 : 1;
					continue;
				}
				if(singleCount == 0 && c == '"'){
					doubleCount = doubleCount == 1 ? 0 : 1;
					continue;
				}
				if(c == '\r' || c == '\n') continue;
				if(scanValue){
					if(singleCount == 1 || doubleCount == 1){
						cache.append(c);
					}
					if(cache.length() != 0 && singleCount == 0 && doubleCount == 0){
						kv[1] = cache.toString();
						cache.setLength(0);
						parameters.put(kv[0], kv[1]);
						scanValue = false;
					}
				}else{
					if(c == '='){
						kv[0] = cache.toString().trim();
						cache.setLength(0);
						scanValue = true;
						continue;
					}else{
						cache.append(c);	
					}
				}
			}
		}
		return parameters;
	}
	
	public static String formatParameters(Map<String, String> parameters) {
		StringBuilder builder = new StringBuilder(" ");
		if(parameters != null && ! parameters.isEmpty()) {
			for(Entry<String, String> entry: parameters.entrySet()) {
				builder.append(entry.getKey() + "=\"" + entry.getValue() + "\" ");
			}
		}
		return builder.toString();
	}
}
