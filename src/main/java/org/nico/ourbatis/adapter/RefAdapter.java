package org.nico.ourbatis.adapter;

import java.util.Map;

import org.nico.ourbatis.el.NoelRender;
import org.nico.ourbatis.utils.StreamUtils;
import org.nico.seeker.dom.DomBean;

public class RefAdapter extends AssistAdapter{

	@Override
	public String adapter(Map<String, Object> datas, NoelRender render, DomBean document) {
		String path = render.rending(datas, document.get("path"), "domainSimpleClassName");
		String result =  StreamUtils.convertToString(path.replaceAll("classpath:", ""));
		return result == null ? "" : result.trim();
	}



}
