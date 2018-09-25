package org.nico.ourbatis.el;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
	
	public void each(Function<Object, String> callBack, Consumer<String> handle) {
		pos = 0;
		for(Object o: list) {
			handle.accept(callBack.apply(o));
			if(! isLast() && split != null) {
				handle.accept(split);
			}
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
