package org.nico.ourbatis.config;

import java.util.HashMap;
import java.util.Map;

import org.nico.ourbatis.adapter.AssistAdapter;
import org.nico.ourbatis.adapter.ForeachAdapter;
import org.nico.ourbatis.adapter.RefAdapter;

public class OurConfig {

	public static String prefix = "@{";
	
	public static String suffix = "}";
	
	public static String baseTemplateUri = "ourbatis.xml";
	
	public static String mapperPacketUri = "org.nico.ourbatis.mapper";
	
	public static Map<String, AssistAdapter> adapterMap = new HashMap<String, AssistAdapter>(){
		private static final long serialVersionUID = 1L;
		{
			
			put("ourbatis:foreach", new ForeachAdapter());
			put("ourbatis:ref", new RefAdapter());
		}
	};
}
