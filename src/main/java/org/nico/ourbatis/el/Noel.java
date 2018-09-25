package org.nico.ourbatis.el;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ourbatis.config.OurConfig;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.ourbatis.utils.ConvertUtils;
import org.nico.seeker.scan.impl.NicoScanner;

public class Noel {
	
	private String prefix;
	
	private String suffix;
	
	private NoelRender render;
	
	public Noel() {
		this(OurConfig.prefix, OurConfig.suffix);
	}

	public Noel(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.render = new NoelRender(prefix, suffix);
	}
	
	@SuppressWarnings("unchecked")
	public NoelWriter el(String document, Object data) {
		AssertUtils.assertNull(data);
		AssertUtils.assertBlank(data);
		final Object source = data instanceof Collection ? ConvertUtils.collectionToMap((Collection<?>) data) : Noson.convert(Noson.reversal(data), new NoType<Map<String, Object>>(){});
		NoelWriter noelWriter = new NoelWriter(new NicoScanner().domScan(document))
				.write(specialDocument -> {
						return OurConfig
								.adapterMap
								.get(specialDocument.getPrefix())
								.adapter((Map<String, Object>) source, render, specialDocument);
						}, body -> {
							for(Entry<String, Object> entry: ((Map<String, Object>) source).entrySet()) {
								body = render.rending(source, body, entry.getKey());
							}
							return body;
						});
		return noelWriter;
	}
	
}
