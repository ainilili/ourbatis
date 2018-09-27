package org.nico.ourbatis.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.nico.ourbatis.el.NoelLooper;
import org.nico.ourbatis.el.NoelRender;
import org.nico.ourbatis.exception.OurbatisException;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.ourbatis.xml.Document;

public class ForeachAdapter extends AssistAdapter{

	@Override
	public String adapter(Map<String, Object> datas, NoelRender render, Document document) {
		String listKey = document.getParameter("list");
		String varKey = document.getParameter("var");
		String split = document.getParameter("split");
		String body = document.getContent().trim();
		
		StringBuilder builder = new StringBuilder();
		if(datas.containsKey(listKey)) {
			Object list = datas.get(listKey);
			AssertUtils.assertBlank(list, "The object of the loop cannot be empty.");
			if(list instanceof Collection) {
				new NoelLooper((List<?>) list)
					.split(split)
					.each(o -> { return render.rending(o, body, varKey);}, builder::append);
				return builder.toString();
			}else {
				throw new OurbatisException("The object of the loop is not a collection.");
			}
		}else {
			return body;
		}
	}


}
