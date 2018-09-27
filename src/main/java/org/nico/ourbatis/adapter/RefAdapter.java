package org.nico.ourbatis.adapter;

import java.util.Map;

import org.nico.ourbatis.el.NoelRender;
import org.nico.ourbatis.utils.StreamUtils;
import org.nico.ourbatis.xml.Document;

public class RefAdapter extends AssistAdapter{

	@Override
	public String adapter(Map<String, Object> datas, NoelRender render, Document document) {
		String path = render.rending(datas, document.getParameter("path"), "domainSimpleClassName");
		String result =  StreamUtils.convertToString(path.replaceAll("classpath:", ""));
		return result == null ? "" : result.trim();
	}



}
