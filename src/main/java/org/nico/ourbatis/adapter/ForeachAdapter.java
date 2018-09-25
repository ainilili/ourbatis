package org.nico.ourbatis.adapter;

import org.nico.ourbatis.el.NoelAssist;
import org.nico.seeker.dom.DomBean;

public class ForeachAdapter extends AssistAdapter{

	@Override
	public String adapter(NoelAssist noelAssist, DomBean document) {
		return noelAssist.foreachAssist(document.getBody().trim(),
				document.get("list"),
				document.get("var"),
				document.get("split"));
	}


}
