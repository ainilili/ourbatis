package org.nico.ourbatis.entity;

import java.util.List;

public class PageResult<T> {
	
	private long total;
	
	private List<T> results;

	public PageResult(long total, List<T> results) {
		this.total = total;
		this.results = results;
	}

	public final long getTotal() {
		return total;
	}

	public final void setTotal(long total) {
		this.total = total;
	}

	public final List<T> getResults() {
		return results;
	}

	public final void setResults(List<T> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "PageResult [total=" + total + ", results=" + results + "]";
	}
	
}
