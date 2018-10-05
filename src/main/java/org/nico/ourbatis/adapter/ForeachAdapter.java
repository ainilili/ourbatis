package org.nico.ourbatis.adapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.ourbatis.el.NoelLooper;
import org.nico.ourbatis.el.NoelRender;
import org.nico.ourbatis.exception.OurbatisException;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.ourbatis.xml.Document;

/**
 * &lt;ourbatis:foreach&gt; Render adapter, The {@link Document} object passed 
 * in by the adapter can contain three parameters {list, var, [split]}
 * <ol>
 * 	<li><strong>list</strong>: The key in the datas metadata, the value corresponding to the key must
 *  be a collection as the target of the loop</li>
 *  <li><strong>var</strong>: An alias name for the traversed element within the scope</li>
 *  <li><strong>split</strong>: If a split exists, the value specified by the split will be spliced 
 *  before and after the element to be looped</li>
 * </ol>
 * 
 * @author nico
 */
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
			final Map<String, Object> sources = new HashMap<>();
			if(list instanceof Collection) {
				new NoelLooper((List<?>) list)
					.split(split)
					.each(o -> { 
						sources.put(varKey, o);
						return render.rending(sources, body, varKey);
					}, builder::append);
				return builder.toString();
			}else {
				throw new OurbatisException("The object of the loop is not a collection.");
			}
		}else {
			return body;
		}
	}


}
