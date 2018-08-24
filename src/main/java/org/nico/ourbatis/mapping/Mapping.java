package org.nico.ourbatis.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.nico.ourbatis.entity.Column;
import org.nico.ourbatis.utils.ArrayUtils;
import org.nico.ourbatis.utils.ReflactUtils;

public class Mapping {

	private Class<?> clazz;
	
	public Mapping(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public List<Column> mappingColumns(){
		Field[] classFields = ReflactUtils.getFields(clazz);
		if(ArrayUtils.isNotEmpty(classFields)) {
			List<Column> columns = new ArrayList<Column>(classFields.length);
			for(Field field: classFields) {
				if(! ReflactUtils.isBuildIgnore(field)) {
					columns.add(mappingColumnEntity(field));
				}
			}
			return columns;
		}
		return null;
	}
	
	private Column mappingColumnEntity(Field field) {
		Column column = new Column();
		column.setJdbcName(MappingWrapper.wrappingFieldWrappers(field.getName()));
		column.setJdbcType(MappingConfig.JAVA_TYPE_MAPPING_MYSQL.get(field.getType()));
		column.setJavaName(field.getName());
		column.setJavaType(field.getType().getSimpleName());
		return column;
	}
	
}
