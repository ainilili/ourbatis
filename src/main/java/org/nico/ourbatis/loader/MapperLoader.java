package org.nico.ourbatis.loader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.nico.ourbatis.contains.OurConnfig;
import org.nico.ourbatis.el.Noel;
import org.nico.ourbatis.el.NoelResult;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.mapping.Mapping;
import org.nico.ourbatis.utils.ClassUtils;
import org.nico.ourbatis.utils.StreamUtils;

/**
 * We need a builder to help us implement the environment in which the processed mapper.xml 
 * is loaded into mybatis, 
 * @author nico
 * @time 2018-08-23 18:22
 */
public class MapperLoader {
	
	private Log log = new Log4j2Impl(this.getClass().getName());
	
	private Queue<String> mappers;
	
	private Mapping mapping;
	
	private Noel noel;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Configuration configuration;
	
	private String baseTemplateUri;
	
	private String baseTemplateContent;
	
	private String mapperPacketUri;
	
	public MapperLoader(SqlSessionFactory sqlSessionFactory, String baseTemplateUri, String mapperPacketUri) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.configuration = sqlSessionFactory.getConfiguration();
		this.mappers = new ConcurrentLinkedQueue<String>();
		this.mapping = new Mapping();
		this.noel = new Noel();
		this.baseTemplateUri = baseTemplateUri;
		this.mapperPacketUri = mapperPacketUri;
		this.baseTemplateContent = StreamUtils.convertToString(baseTemplateUri);
		log.debug(" _____   _   _   _____    _____       ___   _____   _   _____  \r\n" + 
				"/  _  \\ | | | | |  _  \\  |  _  \\     /   | |_   _| | | /  ___/ \r\n" + 
				"| | | | | | | | | |_| |  | |_| |    / /| |   | |   | | | |___  \r\n" + 
				"| | | | | | | | |  _  /  |  _  {   / /_| |   | |   | | \\___  \\ \r\n" + 
				"| |_| | | |_| | | | \\ \\  | |_| |  / /  | |   | |   | |  ___| | \r\n" + 
				"\\_____/ \\_____/ |_|  \\_\\ |_____/ /_/   |_|   |_|   |_| /_____/   ~LOADING\r\n" +
				"===========================================================================\\\\");
	}
	
	public MapperLoader add(Class<?> clazz) {
		log.debug("Loading " + clazz.getSimpleName());
		Table entityInfo = mapping.mappingTable(clazz, mapperPacketUri);
		NoelResult result = noel.el(baseTemplateContent, entityInfo);
		mappers.add(result.getFormat());
		return this;
	}
	
	public MapperLoader add(String packet) {
		try {
			List<Class<?>> classes = ClassUtils.getClasses(packet);
			if(classes != null && ! classes.isEmpty()) {
				classes.forEach(clazz -> {
					add(clazz);
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void build() {
		log.debug("Building");
		if(! mappers.isEmpty()) {
			mappers.forEach(mapper -> {
				InputStream mapperStream = new ByteArrayInputStream(mapper.getBytes());
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperStream, configuration, null, configuration.getSqlFragments());
				xmlMapperBuilder.parse();
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
