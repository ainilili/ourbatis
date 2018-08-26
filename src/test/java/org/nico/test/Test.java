package org.nico.test;

import java.util.Map;

import org.nico.noson.Noson;
import org.nico.ourbatis.builder.MapperBuilder;
import org.nico.ourbatis.config.BaseConfig;
import org.nico.ourbatis.el.MapperEL;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.mapping.Mapping;
import org.nico.ourbatis.utils.StreamUtils;

public class Test {

	public static void main(String[] args) {

		Mapping mapping = new Mapping();
		
		Table table = mapping.mappingTable(TestEntity.class);
		
		Map<String, Object> datas = Noson.convert(Noson.reversal(table), Map.class);
		
		
		MapperEL mapperEL = new MapperEL();
		String temp = StreamUtils.convertToString(BaseConfig.baseTemplateUri);
		
		String result = mapperEL.render(datas, temp);
		MapperBuilder mapperTempBuilder = new MapperBuilder(result);
		String renderMapper = mapperTempBuilder.builder("mapper/TestEntity.xml");
		System.out.println(renderMapper);
	}
}
