package org.nico.ourbatis.entity;

import java.util.List;
import java.util.Map;

/** 
 * Entity classes map to carriers of table information
 * 
 * @author nico
 */
public class Table {
	
	/**
	 * Table name of mapping. The default value is the entity class name before wrapping
	 */
	private String tableName;
	
	/**
	 * Character encoding
	 */
	private String charset;
	
	/**
	 * Host of datasource
	 */
	private String host;
	
	/**
	 * Port of datasource
	 */
	private String port;
	
	/**
	 * Primary columns
	 */
	private List<Column> primaryColumns;
	
	/**
	 * Normal columns
	 */
	private List<Column> normalColumns;
	
	/**
	 * Primary & Normal columns
	 */
	private List<Column> allColumns;
	
	/**
	 * Current domain class
	 */
	private Class<?> domainClass;
	
	/**
	 * Current domain simple class name
	 */
	private String domainSimpleClassName;
	
	/**
	 * Current domain class name
	 */
	private String domainClassName;
	
	/**
	 * Mapper class name
	 */
	private String mapperClassName;
	
	/**
	 * Parameters
	 */
	private Map<String, Object> params;
	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getMapperClassName() {
		return mapperClassName;
	}

	public void setMapperClassName(String mapperClassName) {
		this.mapperClassName = mapperClassName;
	}

	public Class<?> getDomainClass() {
		return domainClass;
	}

	public void setDomainClass(Class<?> domainClass) {
		this.domainClass = domainClass;
	}

	public String getDomainClassName() {
		return domainClassName;
	}

	public void setDomainClassName(String domainClassName) {
		this.domainClassName = domainClassName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public List<Column> getPrimaryColumns() {
		return primaryColumns;
	}

	public void setPrimaryColumns(List<Column> primaryColumns) {
		this.primaryColumns = primaryColumns;
	}

	public List<Column> getNormalColumns() {
		return normalColumns;
	}

	public void setNormalColumns(List<Column> normalColumns) {
		this.normalColumns = normalColumns;
	}

	public List<Column> getAllColumns() {
		return allColumns;
	}

	public void setAllColumns(List<Column> allColumns) {
		this.allColumns = allColumns;
	}

	public String getDomainSimpleClassName() {
		return domainSimpleClassName;
	}

	public void setDomainSimpleClassName(String domainSimpleClassName) {
		this.domainSimpleClassName = domainSimpleClassName;
	}

}
