package org.nico.ourbatis.adapter;

import org.nico.ourbatis.el.NoelAssist;
import org.nico.seeker.dom.DomBean;

public abstract class AssistAdapter {

	public abstract String adapter(NoelAssist noelAssist, DomBean document);
}
