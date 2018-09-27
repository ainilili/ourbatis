package org.nico.ourbatis.el;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ourbatis.OurBatis;
import org.nico.ourbatis.utils.AssertUtils;
import org.nico.ourbatis.utils.ConvertUtils;
import org.nico.ourbatis.xml.SimpleScanner;

public class Noel {
	
	private NoelRender render;
	
	public Noel() {
		this(OurBatis.prefix, OurBatis.suffix);
	}

	public Noel(String prefix, String suffix) {
		this.render = new NoelRender(prefix, suffix);
	}
	
	@SuppressWarnings("unchecked")
	public NoelResult el(String document, Object data) {
		AssertUtils.assertNull(data);
		AssertUtils.assertBlank(data);
		final Object source = data instanceof Collection ? ConvertUtils.collectionToMap((Collection<?>) data) : Noson.convert(Noson.reversal(data), new NoType<Map<String, Object>>(){});
		NoelWriter noelWriter = new NoelWriter(new SimpleScanner(document).scan().results())
				.write(specialDocument -> {
						return OurBatis
								.ASSIST_ADAPTERS
								.get(specialDocument.getName())
								.adapter((Map<String, Object>) source, render, specialDocument);
						}, body -> {
							for(Entry<String, Object> entry: ((Map<String, Object>) source).entrySet()) {
								body = render.rending(source, body, entry.getKey());
							}
							return body;
						});
		return new NoelResult(noelWriter.body(), noelWriter.format());
	}
	
}
