package org.nico.ourbatis.loader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.logging.log4j2.Log4j2LoggerImpl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * We need a builder to help us implement the environment in which the processed mapper.xml 
 * is loaded into mybatis, 
 * @author nico
 * @time 2018-08-23 18:22
 */
public class LoaderMapper {
	
	private Log log = new Log4j2Impl(this.getClass().getName());
	
	private Queue<String> mappers;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Configuration configuration;
	
	public LoaderMapper(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.configuration = sqlSessionFactory.getConfiguration();
		this.mappers = new ConcurrentLinkedQueue<String>();
	}
	
	/**
	 * Append a mapper into mappers
	 * 
	 * @param mapper mapper's content
	 * @return true (as specified by Collection.add)
	 */
	public boolean add(String mapper) {
		return mappers.add(mapper);
	}
	
	public void builder() {
		if(! mappers.isEmpty()) {
			mappers.forEach(mapper -> {
				InputStream mapperStream = new ByteArrayInputStream(mapper.getBytes());
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperStream, configuration, null, configuration.getSqlFragments());
				xmlMapperBuilder.parse();
				log.debug("Load " + mapper + " Successful !!");
			});
		}
	}

	public Queue<String> getMappers() {
		return mappers;
	}

	public void setMappers(Queue<String> mappers) {
		this.mappers = mappers;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
}
