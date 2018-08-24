package org.nico.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.nico.ourbatis.config.BaseConfig;
import org.nico.ourbatis.el.MapperEL;
import org.nico.ourbatis.entity.Column;
import org.nico.ourbatis.mapping.Mapping;
import org.nico.ourbatis.utils.StreamUtils;

public class Test {

	public static void main(String[] args) {
		Mapping mapping = new Mapping(TestEntity.class);
		
		List<Column> ces = mapping.mappingColumns();
		
		MapperEL mapperEL = new MapperEL(BaseConfig.prefix, BaseConfig.suffix);
		String temp = StreamUtils.convertToString(BaseConfig.baseTemplateUri);
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("columns", ces);
		datas.put("pcolumns", ces);
		datas.put("tableName", TestEntity.class.getSimpleName());
		datas.put("domainPackage", "com.nico");
		datas.put("className", TestEntity.class.getSimpleName());
		datas.put("mapperLocation", "mapper");
		datas.put("mapperName", TestEntity.class.getSimpleName());
		String result = mapperEL.render(datas, temp);
		System.out.println(result);
	}
}
