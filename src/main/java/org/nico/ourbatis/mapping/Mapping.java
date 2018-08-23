package org.nico.ourbatis.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.nico.ourbatis.entity.ColumnEntity;
import org.nico.ourbatis.utils.ArrayUtils;
import org.nico.ourbatis.utils.ReflactUtils;

public class Mapping {

	private Class<?> clazz;
	
	public Mapping(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public List<ColumnEntity> mappingColumns(){
		Field[] classFields = ReflactUtils.getFields(clazz);
		if(ArrayUtils.isNotEmpty(classFields)) {
			List<ColumnEntity> columns = new ArrayList<ColumnEntity>(classFields.length);
			for(Field field: classFields) {
				if(! ReflactUtils.isBuildIgnore(field)) {
					ColumnEntity column = new ColumnEntity();
					column.setColumnName(MappingWrapper.wrappingFieldWrappers(field.getName()));
					column.setJavaType(field.getType().getSimpleName());
					column.setJdbcType(MappingConfig.JAVA_TYPE_MAPPING_MYSQL.get(field.getType()));
					columns.add(column);
				}
			}
		}
		return null;
	}
	
}
