package org.nico.ourbatis.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.nico.ourbatis.el.NoelLooper;
import org.nico.ourbatis.el.NoelRender;
import org.nico.ourbatis.exception.OurbatisException;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.seeker.dom.DomBean;

public class ForeachAdapter extends AssistAdapter{

	@Override
	public String adapter(Map<String, Object> datas, NoelRender render, DomBean document) {
		String listKey = document.get("list");
		String varKey = document.get("var");
		String split = document.get("split");
		String body = document.getBody().trim();
		
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
