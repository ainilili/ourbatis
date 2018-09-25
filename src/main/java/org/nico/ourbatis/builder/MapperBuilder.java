package org.nico.ourbatis.builder;

import org.nico.ourbatis.utils.StreamUtils;

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
