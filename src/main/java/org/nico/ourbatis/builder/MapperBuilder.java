package org.nico.ourbatis.builder;

import java.io.IOException;


import org.apache.ibatis.io.Resources;
import org.nico.ourbatis.utils.StreamUtils;
import org.nico.ourbatis.utils.XMLUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class MapperBuilder {

	private String baseTemplateContent;

	public MapperBuilder(String baseTemplateContent) {
		this.baseTemplateContent = baseTemplateContent;
	}

	public String builder(String mapperUri) {
		String mapperContent = StreamUtils.convertToString(mapperUri);
		mapperContent = mapperContent == null ? "" : mapperContent;
		baseTemplateContent = baseTemplateContent.replaceAll("###ourbatis###", mapperContent);
		return baseTemplateContent;
	}

	

}
