package org.nico.ourbatis.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.nico.ourbatis.entity.Column;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.utils.ArrayUtils;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.ourbatis.utils.ReflectUtils;
import org.nico.ourbatis.wrapper.JdbcNameWrapper;
import org.nico.ourbatis.wrapper.JdbcTypeWrapper;
import org.nico.ourbatis.wrapper.MapperNameWrapper;
import org.nico.ourbatis.wrapper.TableNameWrapper;
import org.nico.ourbatis.wrapper.Wrapper;

/**
 * The purpose of Mapping is to parse an entity class and transform it 
 * into database information to serve as metadata when the template is parsed
 * 
 * @author nico
 */
public class MapperMapping {

	private Wrapper<Class<?>> tableNameWrapper;
	
	private Wrapper<Class<?>> mapperNameWrapper;
	
	private Wrapper<Field> JdbcNameWrapper;
	
	private Wrapper<Class<?>> JdbcTypeWrapper;
	
	public MapperMapping() {
		this.tableNameWrapper = new TableNameWrapper();
		this.JdbcNameWrapper = new JdbcNameWrapper();
		this.JdbcTypeWrapper = new JdbcTypeWrapper();
		this.mapperNameWrapper = new MapperNameWrapper();
	}
	
	public Table mappingTable(Class<?> domainClass, String mapperLocations){
		Table table = new Table();
		Field[] classFields = ReflectUtils.getFields(domainClass);
		List<Column> normalColumns = new ArrayList<Column>(classFields.length);
		List<Column> primaryColumns = new ArrayList<Column>(classFields.length);
		List<Column> allColumns = new ArrayList<Column>(classFields.length);
		if(ArrayUtils.isNotEmpty(classFields)) {
			for(Field field: classFields) {
				if(! ReflectUtils.isRenderIgnore(field)) {
					Column column = mappingColumnEntity(field);
					if(ReflectUtils.isRenderPrimary(field)){
						primaryColumns.add(column);
					}else{
						normalColumns.add(column);
					}
					allColumns.add(column);
				}
			}
		}
		AssertUtils.assertNotEmpty(primaryColumns, "The entity class needs at least one primary key");
		AssertUtils.assertNotEmpty(normalColumns, "The entity class needs at least one field");
		
		table.setTableName(tableNameWrapper.wrapping(domainClass));
		table.setPrimaryColumns(primaryColumns);
		table.setNormalColumns(normalColumns);
		table.setAllColumns(allColumns);
		table.setDomainClass(domainClass);
		table.setDomainSimpleClassName(domainClass.getSimpleName());
		table.setDomainClassName(domainClass.getName());
		if(ReflectUtils.isMapperBy(domainClass)) {
			table.setMapperClassName(ReflectUtils.getMapperBy(domainClass).getName());
		}else{
			table.setMapperClassName(mapperLocations + "." + mapperNameWrapper.wrapping(domainClass));
		}
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
