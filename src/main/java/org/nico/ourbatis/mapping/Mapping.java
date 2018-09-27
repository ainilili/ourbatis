package org.nico.ourbatis.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.nico.ourbatis.contains.OurConnfig;
import org.nico.ourbatis.entity.Column;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.utils.ArrayUtils;
import org.nico.ourbatis.utils.ReflactUtils;
import org.nico.ourbatis.wrapper.JdbcNameWrapper;
import org.nico.ourbatis.wrapper.JdbcTypeWrapper;
import org.nico.ourbatis.wrapper.MapperNameWrapper;
import org.nico.ourbatis.wrapper.TableNameWrapper;
import org.nico.ourbatis.wrapper.Wrapper;

public class Mapping {

	private Wrapper<Class<?>> tableNameWrapper;
	
	private Wrapper<Class<?>> mapperNameWrapper;
	
	private Wrapper<Field> JdbcNameWrapper;
	
	private Wrapper<Class<?>> JdbcTypeWrapper;
	
	public Mapping() {
		this.tableNameWrapper = new TableNameWrapper();
		this.JdbcNameWrapper = new JdbcNameWrapper();
		this.JdbcTypeWrapper = new JdbcTypeWrapper();
		this.mapperNameWrapper = new MapperNameWrapper();
	}
	
	public Table mappingTable(Class<?> domainClass, String mapperPacketUri){
		Table table = new Table();
		Field[] classFields = ReflactUtils.getFields(domainClass);
		List<Column> normalColumns = new ArrayList<Column>(classFields.length);
		List<Column> primaryColumns = new ArrayList<Column>(classFields.length);
		List<Column> allColumns = new ArrayList<Column>(classFields.length);
		if(ArrayUtils.isNotEmpty(classFields)) {
			for(Field field: classFields) {
				if(! ReflactUtils.isRenderIgnore(field)) {
					Column column = mappingColumnEntity(field);
					if(ReflactUtils.isRenderPrimary(field)){
						primaryColumns.add(column);
					}else{
						normalColumns.add(column);
					}
					allColumns.add(column);
				}
			}
		}
		table.setTableName(tableNameWrapper.wrapping(domainClass));
		table.setPrimaryColumns(primaryColumns);
		table.setNormalColumns(normalColumns);
		table.setAllColumns(allColumns);
		table.setDomainClass(domainClass);
		table.setDomainSimpleClassName(domainClass.getSimpleName());
		table.setDomainClassName(domainClass.getName());
		table.setMapperClassName(mapperPacketUri + "." + mapperNameWrapper.wrapping(domainClass));
		return table;
	}
	
	private Column mappingColumnEntity(Field field) {
		Column column = new Column();
		column.setJdbcName(JdbcNameWrapper.wrapping(field));
		column.setJdbcType(JdbcTypeWrapper.wrapping(field.getType()));
		column.setJavaName(field.getName());
		column.setJavaType(field.getType().getSimpleName());
		return column;
	}
	
}
