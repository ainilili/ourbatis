package org.nico.test;

import org.nico.ourbatis.el.Noel;
import org.nico.ourbatis.el.NoelResult;
import org.nico.ourbatis.entity.Table;
import org.nico.ourbatis.mapping.Mapping;
import org.nico.ourbatis.utils.StreamUtils;

public class Test {

	public static void main(String[] args) {
		Mapping mapping = new Mapping();
		
		Table table = mapping.mappingTable(TestEntity.class, "org.test");
		
		Noel el = new Noel();
		
		NoelResult result = el.el(StreamUtils.convertToString("ourbatis.xml"), table);
		
		System.out.println(result.getFormat());
		
	}
}
