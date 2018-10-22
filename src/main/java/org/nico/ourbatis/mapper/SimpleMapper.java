package org.nico.ourbatis.mapper;

import java.util.List;

import org.nico.ourbatis.entity.Page;
import org.nico.ourbatis.entity.PageResult;

/**
 * Ourbatis default universal Mapper interface
 * 
 * @author nico
 */
public interface SimpleMapper<T,K>{
	
	/**
	 * Select entity by primary key
	 * 
	 * @param key 
	 * 			Primary key
	 * @return Search result
	 */			
	public T selectById(K key);
	
	/**
	 * Select entity by condition
	 * 
	 * @param condition
	 * 			Query condition
	 * @return Search result
	 */
	public T selectEntity(T condition);
	
	/**
	 * Select entity list by condition
	 * 
	 * @param condition 
	 * 			Query condition
	 * @return Search result
	 */
	public List<T> selectList(T condition);
	
	/**
	 * Select count by contidion
	 * 
	 * @param condition 
	 * 			Query condition
	 * @return The number of queries
	 */
	public long selectCount(Object condition);
	
	/**
	 * Paging query
	 * 
	 * @param page {@link Page}
	 * @return Search result
	 */
	public List<T> selectPage(Page<Object> page);
	
	/**
	 * PageResult query, A federated query of selectCount and selectPage
	 * 
	 * @param page {@link Page}
	 * @return Search result
	 */
	default PageResult<T> selectPageResult(Page<Object> page){
		long total = selectCount(page.getEntity());
		List<T> results = null;
		if(total > 0) {
			results = selectPage(page);
		}
		return new PageResult<>(total, results);
	}
	
	/**
	 * Select primary key by condition
	 * 
	 * @param condition 
	 * 			Query condition
	 * @return Primary key
	 */
	public K selectId(T condition);
	
	/**
	 * Select list of primary key by condition
	 * 
	 * @param condition
	 * 			Query condition
	 * @return List of primary key
	 */
	public List<K> selectIds(T condition);
	
	/**
	 * Insert entity
	 * 
	 * @param entity 
	 * 			Entity
	 * @return Modify the quantity
	 */
	public int insert(T entity);
	
	/**
	 * Insert entity where the field is not null
	 * 
	 * @param entity
	 * 			Entity
	 * @return Modify the quantity
	 */
	public int insertSelective(T entity);
	
	/**
	 * Insert entity list
	 * 
	 * @param list 
	 * 			Entity list
	 * @return Modify the quantity
	 */
	public int insertBatch(List<T> list);
	
	/**
	 * Update entity
	 * 
	 * @param entity
	 * 			Entity
	 * @return Modify the quantity
	 */
	public int update(T entity);
	
	/**
	 * Update entity where the field is not null
	 * 
	 * @param entity
	 * 			Entity
	 * @return Modify the quantity
	 */
	public int updateSelective(T entity);
	
	/**
	 * Update entity list
	 * 
	 * @param list 
	 * 			Entity list
	 * @return Modify the quantity
	 */
	public int updateBatch(List<T> list);
	
	/**
	 * Delete entity by condition
	 * 
	 * @param condition
	 * 			Query condition
	 * @return Modify the quantity
	 */
	public int delete(T condition);
	
	/**
	 * Delete by primary key
	 * 
	 * @param key 
	 * 			Primary key
	 * @return Modify the quantity
	 */
	public int deleteById(K key);
	
	/**
	 * Delete by list of primary key
	 * 
	 * @param list
	 * 			List of primary key
	 * @return Modify the quantity
	 */
	public int deleteBatch(List<K> list);

}