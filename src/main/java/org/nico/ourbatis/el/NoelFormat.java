package org.nico.ourbatis.el;

import java.util.List;

import org.nico.ourbatis.xml.Document;
import org.nico.ourbatis.xml.DocumentType;
import org.nico.ourbatis.xml.DocumentUtils;
import org.nico.ourbatis.xml.SimpleScanner;

public class NoelFormat {

	private String document;

	private StringBuilder builder = new StringBuilder();

	public NoelFormat(String document) {
		this.document = document;
	}

	public void append(String body) {
		this.builder.append(body);
	}

	public String result() {
		return builder.toString();
	}

	public NoelFormat format(){
		format(new SimpleScanner(document).scan().results(), 0);
		return this;
	}

	private NoelFormat format(List<Document> documents, int n){
		StringBuilder tab = new StringBuilder();
		int tn = n;
		while(tn -- > 0) tab.append("\t");
		for(int index = 0; index < documents.size(); index ++){
			Document document = documents.get(index);
			
			if(document.getBeforeContent() != null) {
				String[] contents = document.getBeforeContent().split(System.lineSeparator());
				for(String content: contents) {
					builder.append(tab);
					builder.append(content.trim());
					builder.append(System.lineSeparator());
				}
			}
			String paramStr = DocumentUtils.formatParameters(document.getParameters());
			if(document.getType() == DocumentType.SINGLE){
				builder.append(tab);
				builder.append("<" + document.getName() + " " + paramStr + " />");
				builder.append(System.lineSeparator());
			}else{
				builder.append(tab);
				builder.append("<" + document.getName() + " " + paramStr + " >");
				builder.append(System.lineSeparator());
				if(document.getChilds() != null && ! document.getChilds().isEmpty()){
					format(document.getChilds(), n + 1);
				}else{
					builder.append(tab);
					builder.append(document.getContent());
					builder.append(System.lineSeparator());
				}
				builder.append(tab);
				builder.append("</"+ document.getName() + ">");
				builder.append(System.lineSeparator());
			}
			if(index == documents.size() - 1) {
				if(document.getAfterContent() != null) {
					String[] contents = document.getAfterContent().split(System.lineSeparator());
					for(String content: contents) {
						builder.append(tab);
						builder.append(content.trim());
						builder.append(System.lineSeparator());
					}
				}
			}
		}
		return this;
	}

}
