package org.nico.ourbatis.mapper;

import java.util.List;
import org.nico.ourbatis.entity.Page;

/**
 * Ourbatis default universal Mapper interface
 * 
 * @author nico
 */
public interface OurbatisMapper<T,K>{

	public T selectById(K key);
	public T selectEntity(T t);
	public long selectCount(T t);
	public List<T> selectPage(Page<T> page);
	public List<T> selectList(T t);
	public K selectId(T t);
	public List<K> selectIds(T t);
	public int insert(T t);
	public int insertSelective(T t);
	public int insertBatch(List<T> list);
	public int update(T t);
	public int updateSelective(T t);
	public int updateBatch(List<T> list);
	public int delete(T t);
	public int deleteById(K key);
	public int deleteBatch(List<K> list);

}