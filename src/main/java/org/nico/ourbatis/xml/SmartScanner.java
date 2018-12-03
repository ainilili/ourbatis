package org.nico.ourbatis.xml;

import java.util.List;

public abstract class SmartScanner {

	private String value;

	private int pos = 0;
	
	protected StringBuilder builder = new StringBuilder();
	
	private Status status = Status.START;
	
	public SmartScanner(String value) {
		this.value = value;
	}

	public SmartScanner scan() {
		if(value != null) {
			char[] chars = value.toCharArray();
			Status tempStatus = null;
			for(; pos < chars.length; pos ++) {
				char c = chars[pos];
				if(status == Status.START) {
					tempStatus = parseStart(c);
				}else if(status == Status.HEAD) {
					tempStatus = parseHead(c);
				}else if(status == Status.ANNOTATION){
					tempStatus = parseAnnotation(c);
				} if(status == Status.PARAM) {
					tempStatus = parseParam(c);
				}else if(status == Status.BODY) {
					tempStatus = parseBody(c);
				}
				status = tempStatus;
			}
		}
		return this;
	}
	
	/**
	 * Find />
	 * 
	 * @return
	 */
	protected boolean isClose() {
		char[] chars = value.toCharArray();
		boolean quoFlag = true;
		for(int index = pos; index < chars.length; index ++) {
			char cc = chars[index];
			if(cc == '\'' || cc == '"') {
				quoFlag = ! quoFlag;
			}
			if(quoFlag) {
				if(cc == '>') {
					return false;
				}
				if(cc == '/') {
					if(index < chars.length - 1) {
						if(chars[index + 1] == '>') {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	protected abstract Status parseStart(char c);
	
	protected abstract Status parseHead(char c);
	
	protected abstract Status parseAnnotation(char c);
	
	protected abstract Status parseParam(char c);
	
	protected abstract Status parseBody(char c);
	
	protected abstract Status parseFinished();
	
	public abstract List<Document> results();
	

	protected void append(char c) {
		builder.append(c);
	}
	
	protected String cut(int start, int length) {
		return value.substring(start, start + length);
	}
	
	protected String cut(int length) {
		return cut(pos, length);
	}
	
	protected boolean isLast() {
		return pos == value.length() - 1;
	}
	
	protected boolean move(int length) {
		pos += length;
		return true;
	}
	
	public enum Status{
		
		START,
		
		HEAD,
		
		PARAM,
		
		BODY,
		
		ANNOTATION
		
	}
	
}
