package org.nico.ourbatis.entity;

/** 
 * Help paging query
 * 
 * @author nico
 */
public class Page<T> {

	protected Long start;
	
	protected Long end;
	
	protected String orderBy;
	
	protected T entity;
	
	public static <T> Page<T> start(long page, long length){
		return start(page, length, null, null);
	}

	public static <T> Page<T> start(long page, long length, T entity){
		return start(page, length, entity, null);
	}
	
	public static <T> Page<T> start(long page, long length, String orderBy){
		return start(page, length, null, orderBy);
	}
	
	public static <T> Page<T> start(Long page, Long length, T entity, String orderBy){
		if(page < 1) page = 1L;
		if(length < 1) length = 0L;
		long start = (page - 1) * length;
		long end = length;
		return new Page<T>().setStart(start).setEnd(end).setEntity(entity).setOrderBy(orderBy);
	}
	
	public static <T> Page<T> start(String orderBy){
		return start(null, null, null, orderBy);
	}
	
	public static <T> Page<T> start(T entity, String orderBy){
		return start(null, null, entity, orderBy);
	}

	public Page<T> setStart(Long start) {
		this.start = start;
		return this;
	}

	public Page<T> setEnd(Long end) {
		this.end = end;
		return this;
	}

	public Page<T> setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public Page<T> setEntity(T entity) {
		this.entity = entity;
		return this;
	}
	
}
