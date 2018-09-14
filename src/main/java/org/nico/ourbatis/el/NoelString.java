package org.nico.ourbatis.el;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NoelString {

	private String source;

	private Map<String, Integer> poss;
	
	public NoelString(String source) {
		this.source = source;
		this.poss   = new ConcurrentHashMap<String, Integer>();
	}
	
	public int header(String str) {
		int pos = 0;
		if(poss.containsKey(str)) {
			pos = poss.get(str);
		}
		pos = source.indexOf(str, pos);
		poss.putIfAbsent(str, pos);
		return pos;
	}
	
}
