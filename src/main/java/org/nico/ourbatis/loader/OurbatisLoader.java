package org.nico.ourbatis.loader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.nico.ourbatis.Ourbatis;
import org.nico.ourbatis.el.Noel;
import org.nico.ourbatis.el.NoelResult;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.mapping.MapperMapping;
import org.nico.ourbatis.utils.ClassUtils;
import org.nico.ourbatis.utils.StreamUtils;

/**
 * We need a builder to help us implement the environment in which the processed mapper.xml 
 * is loaded into mybatis, 
 * @author nico
 * @time 2018-08-23 18:22
 */
public class OurbatisLoader {

	private static final Log log = LogFactory.getLog(OurbatisLoader.class);
	
	/**
	 * Template information queue
	 */
	private Map<Table, String> mappers;

	private MapperMapping mapper;

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
		this.mappers = new LinkedHashMap<Table, String>();
		this.mapper = new MapperMapping();
		this.noel = new Noel();
		this.baseTemplateUri = baseTemplateUri;
		this.mapperLocations = mapperLocations;
		this.baseTemplateContent = StreamUtils.convertToString(this.baseTemplateUri);
		log.debug("Start processing the data source session factory " + sqlSessionFactory);
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
		log.debug(">>  Loading " + clazz.getName());
		Table entityInfo = mapper.mappingTable(clazz, mapperLocations);
		if(ClassUtils.forName(entityInfo.getMapperClassName()) != null) {
			NoelResult result = noel.el(baseTemplateContent, entityInfo);
			mappers.put(entityInfo, result.getFormat());
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

	/**
	 * Calling this method will load the template information from the queue into the Mybatis container in turn
	 */
	public void build() {
		if (!this.mappers.isEmpty()) {
			boolean openPrint = Ourbatis.print != null;
			this.mappers.forEach((entityInfo, mapper) -> {
				try {
					if (openPrint) {
						StreamUtils.write(Ourbatis.print, entityInfo.getDomainClassName(), ".xml", mapper);
						log.debug(">>  Writing  " + entityInfo.getDomainClassName());
					}
				} catch (Exception arg6) {
					arg6.printStackTrace();
				}
				log.debug(">>  Building " + entityInfo.getDomainClassName());
				ByteArrayInputStream mapperStream = new ByteArrayInputStream(mapper.getBytes());
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperStream, this.configuration,
						mapperStream.toString(), this.configuration.getSqlFragments());
				xmlMapperBuilder.parse();
			});
		}
	}

	public Map<Table, String> getMappers() {
		return mappers;
	}

	public void setMappers(Map<Table, String> mappers) {
		this.mappers = mappers;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
