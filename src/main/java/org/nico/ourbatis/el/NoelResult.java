package org.nico.ourbatis.el;

public class NoelResult {

	private String source;
	
	private String format;

	public NoelResult(String source, String format) {
		super();
		this.source = source.trim();
		this.format = format.trim();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
}
