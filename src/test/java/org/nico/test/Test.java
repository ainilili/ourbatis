package org.nico.test;

import java.util.Map;

import org.nico.noson.Noson;
import org.nico.ourbatis.config.OurConfig;
import org.nico.ourbatis.el.Noel;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.mapping.Mapping;
import org.nico.ourbatis.utils.StreamUtils;

public class Test {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Mapping mapping = new Mapping();
		
		Table table = mapping.mappingTable(TestEntity.class);
		Map<String, Object> datas = Noson.convert(Noson.reversal(table), Map.class);
//		MapperEL mapperEL = new MapperEL();
//		String temp = StreamUtils.convertToString(BaseConfig.baseTemplateUri);
//		
//		
//		
//		String result = mapperEL.render(datas, temp);
//		MapperBuilder mapperTempBuilder = new MapperBuilder(result);
//		String renderMapper = mapperTempBuilder.builder("mapper/TestEntity.xml");
//		System.out.println(renderMapper);
		String document = StreamUtils.convertToString(OurConfig.baseTemplateUri);
		Noel noel = new Noel("@{", "}");
		
		String rended = noel.el(document, datas);
		System.out.println(rended);
		
	}
}
