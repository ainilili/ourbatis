package org.nico.test;

import org.nico.ourbatis.config.OurConfig;
import org.nico.ourbatis.el.Noel;
import org.nico.ourbatis.el.NoelWriter;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.mapping.Mapping;
import org.nico.ourbatis.utils.StreamUtils;

public class Test {

	public static void main(String[] args) {
		
		//获取Mapping对象
		Mapping mapping = new Mapping();
		
		//获取Bean后的信息
		Table table = mapping.mappingTable(TestEntity.class);
		
		//获取模板
		String template = StreamUtils.convertToString(OurConfig.baseTemplateUri);
		
		//获取el工具对象
		Noel noel = new Noel("@{", "}");
		
		//渲染模板
		NoelWriter writer = noel.el(template, table);
		
		//打印
//		System.out.println(writer.body());
		
		//格式化打印
		System.out.println(writer.format());
	}
}
