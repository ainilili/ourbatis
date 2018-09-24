package org.nico.ourbatis.el;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NoelLooper {

	private List<?> list;

	private String split;
	
	private int pos = 0;
	
	public NoelLooper(List<?> list) {
		this.list = list;
	}
	
	public NoelLooper split(String split) {
		this.split = split;
		return this;
	}
	
	public void each(Consumer<Object> callBack) {
		pos = 0;
		for(Object o: list) {
			callBack.accept(o);
			pos ++;
		}
	}
	
	public boolean isLast() {
		return pos == list.size() - 1;
	}
	
	public String split() {
		return this.split;
	}
	
	
}
