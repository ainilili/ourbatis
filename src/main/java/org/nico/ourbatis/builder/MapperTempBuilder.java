package org.nico.ourbatis.builder;

import java.io.IOException;


import org.apache.ibatis.io.Resources;
import org.nico.ourbatis.utils.XMLUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class MapperTempBuilder {

	private String baseTemplateContent;

	public MapperTempBuilder(String baseTemplateContent) {
		this.baseTemplateContent = baseTemplateContent;
	}

	public String builder(String mapperUri) {
		String mapper = null;
		try {
			Document documnet = XMLUtils.createDocument(new InputSource(Resources.getResourceAsStream(mapperUri)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapper;
	}

	

}
