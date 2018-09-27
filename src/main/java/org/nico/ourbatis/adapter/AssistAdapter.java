package org.nico.ourbatis.adapter;

import java.util.Map;

import org.nico.ourbatis.el.NoelRender;
import org.nico.ourbatis.xml.Document;

public abstract class AssistAdapter {

	public abstract String adapter(Map<String, Object> datas, NoelRender render, Document document);
}
