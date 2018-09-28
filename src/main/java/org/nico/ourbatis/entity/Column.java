package org.nico.ourbatis.entity;

/**
 * Column info
 * 
 * @author nico
 */
public class Column {
	
	/**
	 * Database field name
	 */
	private String jdbcName;
	
	/**
	 * Java field name
	 */
	private String javaName;
	
	/**
	 * Database field type
	 */
	private String jdbcType;
	
	/**
	 * Java field type
	 */
	private String javaType;

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public String getJdbcName() {
		return jdbcName;
	}

	public void setJdbcName(String jdbcName) {
		this.jdbcName = jdbcName;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	@Override
	public String toString() {
		return "Column [jdbcName=" + jdbcName + ", javaName=" + javaName + ", jdbcType=" + jdbcType + ", javaType="
				+ javaType + "]";
	}
	
}
