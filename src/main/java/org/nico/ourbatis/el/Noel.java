package org.nico.ourbatis.el;

import java.util.List;
import java.util.Map;

import org.nico.ourbatis.config.BaseConfig;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.scan.SeekerScanner;
import org.nico.seeker.scan.impl.NicoScanner;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.seeker.searcher.impl.NicoSearcher;

public class Noel {
	
	private String prefix;
	
	private String suffix;
	
	public Noel() {
		this.prefix = BaseConfig.preffix;
		this.suffix = BaseConfig.suffix;
	}

	public Noel(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	public String el(String document, Map<String, Object> datas) {
		NoelAssist noelAssist = new NoelAssist(datas, new NoelRender(prefix, suffix));
		NoelWriter noelWriter = new NoelWriter(new NicoScanner().domScan(document));
		noelWriter.write(specialDomcument -> {
			if(specialDomcument.getPrefix().equalsIgnoreCase("ob:foreach")) {
				noelWriter.append(noelAssist.foreachAssist(specialDomcument));
			}
		});
		String result = noelAssist.propertiesAssist(noelWriter.body());
		return result;
	}
}
