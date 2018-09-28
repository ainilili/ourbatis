package org.nico.ourbatis.adapter;

import java.util.Map;

import org.nico.ourbatis.el.NoelRender;
import org.nico.ourbatis.xml.Document;

/**
 * This is an adapter abstract class that defines that each adapter must implement the 
 * adapter method, and after adding an adapter, it should be added to the ASSIST_ADAPTERS 
 * dictionary under the {@link Ourbatis} class to take effect.
 * 
 * @author nico
 */
public abstract class AssistAdapter {
	
	/**
	 * Adapt abstract methods
	 * 
	 * @param datas
	 * 			Metadata
	 * @param render
	 * 			The template to be rendered
	 * @param document
	 * 			Document
	 * @return
	 * 			Render the result
	 */
	public abstract String adapter(Map<String, Object> datas, NoelRender render, Document document);
}
