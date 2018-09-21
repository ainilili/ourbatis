package org.nico.ourbatis.el;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.ourbatis.config.BaseConfig;
import org.nico.ourbatis.exception.OurbatisException;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.seeker.dom.DomBean;

public class NoelAssist {

	private Map<String, Object> datas;
	
	private NoelRender render;
	
	public NoelAssist(Map<String, Object> datas, NoelRender render) {
		this.datas = datas;
		this.render = render;
	}
	
	public String propertiesAssist(String body) {
		StringBuilder builder = new StringBuilder();
		for(Entry<String, Object> entry: datas.entrySet()) {
			String rended = render.rending(entry.getValue(), body, entry.getKey());
			builder.append(rended);
		}
		return builder.toString();
	}
	
	public String foreachAssist(DomBean dom) {
		String listKey = dom.get("list");
		String varKey = dom.get("var");
		String split = dom.get("split");
		String body = dom.getBody();
		StringBuilder builder = new StringBuilder();
		if(datas.containsKey(listKey)) {
			Object list = datas.get(listKey);
			AssertUtils.assertBlank(list, "The object of the loop cannot be empty.");
			if(list instanceof Collection) {
				NoelLooper looper = new NoelLooper((List<?>) list);
				looper
				.split(split)
					.each(o -> {
						String rended = render.rending(o, body, varKey);
						if(! looper.isLast() && looper.split() != null) {
							rended += looper.split();
						}
						builder.append(rended);
					});
				return builder.toString();
			}else {
				throw new OurbatisException("The object of the loop is not a collection.");
			}
		}else {
			return dom.getBody();
		}
	}
}
