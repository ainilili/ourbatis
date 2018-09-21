package org.nico.ourbatis.config;

import java.util.HashSet;
import java.util.Set;

public class BaseConfig {

	public static String preffix = "@{";
	
	public static String suffix = "}";
	
	public static String baseTemplateUri = "ourbatis_xml.tmp";
	
	public static String mapperPacketUri = "org.nico.ourbatis.mapper";
	
	public static Set<String> targets = new HashSet<String>() {
		private static final long serialVersionUID = -1484382244300061718L;
		{
			add("ob:foreach");
		}
	};
}
