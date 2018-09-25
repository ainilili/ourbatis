package org.nico.ourbatis.xml;

import org.nico.ourbatis.exception.OurbatisException;

public class SmartString {

	private String value;

	private int pos = 0;
	
	public SmartString(String value) {
		this.value = value;
	}
	
	public int indexOf(String... strs) {
		int near = -1;
		for(String str: strs) {
			int index = value.indexOf(str);
			near = near > index ? index : near;
		}
		return near;
	}
	
	public String sub(int endIndex) {
		if(endIndex < pos) {
			throw new OurbatisException("End index less than current position：" + endIndex + "<" + pos);
		}
		return value.substring(pos, endIndex);
	}
	
	public void jump(int pos) {
		this.pos = pos;
	}
	
	public void move(int v) {
		this.pos += v;
	}
	
}
