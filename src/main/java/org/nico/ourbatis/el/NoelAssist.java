package org.nico.ourbatis.el;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.ourbatis.exception.OurbatisException;
import org.nico.ourbatis.utils.AssertUtils;

public class NoelAssist {

	private Map<String, Object> datas;

	private NoelRender render;

	public NoelAssist(Map<String, Object> datas, NoelRender render) {
		this.datas = datas;
		this.render = render;
	}

	public String propertiesAssist(String body) {
		StringBuilder builder = new StringBuilder();
		String rended = body;
		for(Entry<String, Object> entry: datas.entrySet()) {
			rended = render.rending(datas, rended, entry.getKey());
		}
		builder.append(rended);
		return builder.toString();
	}

	public String foreachAssist(String body, String listKey, String varKey, String split) {
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
