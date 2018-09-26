package org.nico.test;

import java.util.Collections;
import java.util.List;

import org.nico.ourbatis.config.OurConfig;
import org.nico.ourbatis.utils.StreamUtils;
import org.nico.ourbatis.xml.SimpleScanner;
import org.nico.ourbatis.xml.XMLDocument;

public class TestXML {

	public static void main(String[] args) {
		String xml = StreamUtils.convertToString(OurConfig.baseTemplateUri);
		SimpleScanner scanner = new SimpleScanner(xml);
		List<XMLDocument> docs = scanner.scan().results();
		System.out.println(format(docs, 0));
	}
	
	public static String format(List<XMLDocument> docs, int n) {
		int tn = n;
		StringBuilder builder = new StringBuilder();
		while(tn -- > 0) {
			builder.append("\t");
		}
		StringBuilder content = new StringBuilder();
		if(docs != null) {
			for(XMLDocument doc: docs) {
				content.append(builder.toString() + doc + "\n");
				content.append(format(doc.getChildDocuments(), n + 1));
			}
		}
		return content.toString();
	}
}
