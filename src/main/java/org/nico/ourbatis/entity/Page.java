package org.nico.ourbatis.entity;

/** 
 * Help paging query
 * 
 * @author nico
 */
public class Page<T> {
	
	/**
	 * Limit start
	 */
	protected Long start;
	
	/**
	 * Limit end
	 */
	protected Long end;
	
	/**
	 * Sorting conditions, example "create_time desc"
	 */
	protected String orderBy;
	
	/**
	 * Query condition
	 */
	protected T entity;
	
	public static Page<Object> start(long page, long length){
		return start(page, length, null, null);
	}

	public static Page<Object> start(long page, long length, Object entity){
		return start(page, length, entity, null);
	}
	
	public static Page<Object> start(long page, long length, String orderBy){
		return start(page, length, null, orderBy);
	}
	
	public static Page<Object> start(Long page, Long length, Object entity, String orderBy){
		Long start = null;
		Long end = null;
		if(page != null && length != null) {
			if(page < 1) page = 1L;
			if(length < 1) length = 0L;
			start = (page - 1) * length;
			end = length;
		}
		return new Page<Object>().setStart(start).setEnd(end).setEntity(entity).setOrderBy(orderBy);
	}
	
	public static Page<Object> start(String orderBy){
		return start(null, null, null, orderBy);
	}
	
	public static Page<Object> start(Object entity, String orderBy){
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

	public final T getEntity() {
		return entity;
	}
	
}
