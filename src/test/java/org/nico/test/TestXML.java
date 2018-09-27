package org.nico.test;

import java.util.List;

import org.nico.ourbatis.contains.OurConnfig;
import org.nico.ourbatis.utils.StreamUtils;
import org.nico.ourbatis.xml.Document;
import org.nico.ourbatis.xml.SimpleScanner;

public class TestXML {

	public static void main(String[] args) {
		String xml = StreamUtils.convertToString("ourbatis.xml");
		SimpleScanner scanner = new SimpleScanner(xml);
		List<Document> docs = scanner.scan().results();
		System.out.println(format(docs, 0));
	}
	
	public static String format(List<Document> docs, int n) {
		int tn = n;
		StringBuilder builder = new StringBuilder();
		while(tn -- > 0) {
			builder.append("\t");
		}
		StringBuilder content = new StringBuilder();
		if(docs != null) {
			for(Document doc: docs) {
				content.append(builder.toString() + doc + "\n");
				content.append(format(doc.getChilds(), n + 1));
			}
		}
		return content.toString();
	}
}
