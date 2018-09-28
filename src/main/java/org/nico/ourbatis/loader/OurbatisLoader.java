package org.nico.ourbatis.loader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.nico.noson.util.string.StringUtils;
import org.nico.ourbatis.Ourbatis;
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
public class OurbatisLoader {
	
	/**
	 * Template information queue
	 */
	private Map<Class<?>, String> mappers;
	
	private Mapping mapping;
	
	private Noel noel;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Configuration configuration;
	
	private String baseTemplateUri;
	
	private String baseTemplateContent;
	
	private String mapperLocations;
	
	/**
	 * Get an instance of o through which mapper can be loaded into the mybatis container
	 * 
	 * @param sqlSessionFactory	
	 * 			Creates an SqlSession out of a connection or a DataSource
	 * @param baseTemplateUri
	 * 			The uri to the template, which is the relative path of the template under the classpath
	 * @param mapperPacketUri
	 * 			The path of mapper's package, example 'org.nico.ourbatis.mapper', Store the Mapper interface internally
	 */
	public OurbatisLoader(SqlSessionFactory sqlSessionFactory, String baseTemplateUri, String mapperLocations) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.configuration = sqlSessionFactory.getConfiguration();
		this.mappers = new LinkedHashMap<Class<?>, String>();
		this.mapping = new Mapping();
		this.noel = new Noel();
		this.baseTemplateUri = baseTemplateUri;
		this.mapperLocations = mapperLocations;
		this.baseTemplateContent = StreamUtils.convertToString(this.baseTemplateUri);
		System.out.println("Ourbatis ->>>>>> Session " + sqlSessionFactory);
	}
	
	/**
	 * After a class is parsed as a template, it is added to the load queue. 
	 * When the build method is called, the template content in the queue is 
	 * loaded in turn into the Mybatis container
	 * 
	 * @param clazz
	 * 			Target class
	 * @return This instance 		
	 */
	public OurbatisLoader add(Class<?> clazz) {
		System.out.println("Ourbatis ->> Loading " + clazz.getName());
		Table entityInfo = mapping.mappingTable(clazz, mapperLocations);
		if(ClassUtils.forName(entityInfo.getMapperClassName()) != null) {
			NoelResult result = noel.el(baseTemplateContent, entityInfo);
			mappers.put(clazz, result.getFormat());
		}
		return this;
	}
	
	/**
	 * Add all the class files under the package by scanning the package path
	 * 
	 * @param packet 
	 * 			Package path
	 * @return This instance
	 */
	public OurbatisLoader add(String packet) {
		try {
			Set<Class<? extends Class<?>>> classes = ClassUtils.getClasses(packet);
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
	
	/**
	 * Calling this method will load the template information from the queue into the Mybatis container in turn
	 */
	public void build() {
		if(! mappers.isEmpty()) {
			boolean openPrint = Ourbatis.print != null;
			mappers.forEach((clazz, mapper) -> {
				System.out.println("Ourbatis ->> Building " + clazz.getName());
				InputStream mapperStream = new ByteArrayInputStream(mapper.getBytes());
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperStream, configuration, null, configuration.getSqlFragments());
				xmlMapperBuilder.parse();
				try {
					if(openPrint) {
						StreamUtils.write(Ourbatis.print, clazz.getName(), ".xml", mapper);
						System.out.println("Ourbatis ->> Writing  " + clazz.getName());
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			});
		}
		System.out.println();
	}
	
	public final Map<Class<?>, String> getMappers() {
		return mappers;
	}

	public final void setMappers(Map<Class<?>, String> mappers) {
		this.mappers = mappers;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
}
