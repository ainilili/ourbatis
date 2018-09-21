package org.nico.ourbatis.entity;

import java.util.List;
import java.util.Map;

/** 
 * 
 * @author nico
 * @version createTime：2018年8月26日 下午4:52:15
 */

public class Table {

	private String tableName;
	
	private String charset;
	
	private String host;
	
	private String port;
	
	private List<Column> primaryColumns;
	
	private List<Column> normalColumns;
	
	private List<Column> allColumns;
	
	private Class<?> domainClass;
	
	private String domainClassName;
	
	private String mapperClassName;
	
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

}
