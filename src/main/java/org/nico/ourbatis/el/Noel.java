package org.nico.ourbatis.el;

import java.util.Collection;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ourbatis.config.OurConfig;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.ourbatis.utils.ConvertUtils;
import org.nico.seeker.scan.impl.NicoScanner;

public class Noel {
	
	private String prefix;
	
	private String suffix;
	
	public Noel() {
		this.prefix = OurConfig.preffix;
		this.suffix = OurConfig.suffix;
	}

	public Noel(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	public String el(String document, Object data) {
		AssertUtils.assertNull(data);
		AssertUtils.assertBlank(data);
		if(data instanceof Collection) {
			data = ConvertUtils.collectionToMap((Collection<?>) data);
		}else {
			data = Noson.convert(Noson.reversal(data), new NoType<Map<String, Object>>(){});
		}
		@SuppressWarnings("unchecked")
		NoelAssist noelAssist = new NoelAssist((Map<String, Object>) data, new NoelRender(prefix, suffix));
		NoelWriter noelWriter = new NoelWriter(new NicoScanner().domScan(document));
		noelWriter.write(specialDocument -> {
			return OurConfig
					.adapterMap
					.get(specialDocument.getPrefix())
					.adapter(noelAssist, specialDocument);
		});
		String result = noelAssist.propertiesAssist(noelWriter.body());
		return result;
	}
	
}
