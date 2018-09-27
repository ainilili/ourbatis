package org.nico.ourbatis.xml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nico.noson.util.string.StringUtils;

public class SimpleScanner extends SmartScanner{

	private Document currentDocument;
	
	private LinkedList<Document> results;
	
	private int singleCount = 0;
	
	private int doubleCount = 0;
	
	private int tagCount = 0;
	
	private static Map<String, String> specialNames = new HashMap<String, String>(){
		private static final long serialVersionUID = 6675296460361731643L;
		{
			put("?xml", "?");
			put("!--", "--");
			put("!DOCTYPE", "html");
		}
	};
	
	public SimpleScanner(String value) {
		super(value);
		results = new LinkedList<Document>();
	}

	@Override
	protected Status parseStart(char c) {
		if(c == '<') {
			currentDocument = new Document();
			currentDocument.setBeforeContent(builder.toString());
			if(! results.isEmpty()) {
				Document lastDoc = results.getLast();
				currentDocument.setPre(lastDoc);
				lastDoc.setNext(currentDocument);
				lastDoc.setAfterContent(currentDocument.getBeforeContent());
			}
			builder.setLength(0);
			return Status.HEAD;
		}else if(isLast()){
			append(c);
			if(! results.isEmpty()) {
				results.getLast().setAfterContent(builder.toString());
			}
			builder.setLength(0);
		}else {
			append(c);
		}
		return Status.START;
	}
	
	@Override
	protected Status parseHead(char c) {
		if(builder.toString().equals("!--")) {
			return Status.ANNOTATION;
		}
		if(c == ' ' || c == '\t' || c == '>' || cut(2).equals("/>") || (specialNames.containsKey(builder.toString()) && cut(2).equals(specialNames.get(builder.toString())))) {
			String name = builder.toString();
			currentDocument.setName(name);
			tagCount ++;
			builder.setLength(0);
			if(c == '>') {
				currentDocument.setType(DocumentType.DOUBLE);
				return Status.BODY;
			}else if(cut(2).equals("/>")){
				currentDocument.setType(DocumentType.SINGLE);
				return Status.BODY;
			}else {
				return Status.PARAM;
			}
		}else {
			append(c);
		}
		return Status.HEAD;
	}
	
	@Override
	protected Status parseAnnotation(char c) {
		append(c);
		if(builder.toString().endsWith("-->")) {
			builder.setLength(0);
			return Status.START;
		}
		return Status.ANNOTATION;
	}

	@Override
	protected Status parseParam(char c) {
		quotesFilter(c);
		if(quotesIsClose() && (c == '>' || cut(2).equals("/>") || (specialNames.containsKey(currentDocument.getName()) && cut(1).equals(specialNames.get(currentDocument.getName()))))) {
			String params = builder.toString();
			currentDocument.setParameters(DocumentUtils.parseParameters(params));
			builder.setLength(0);
			if(c == '>') {
				currentDocument.setType(DocumentType.DOUBLE);
				return Status.BODY;
			}else if(cut(2).equals("/>")){
				move(1);
				currentDocument.setType(DocumentType.SINGLE);
				return parseFinished();
			}else {
				move(1);
				currentDocument.setType(DocumentType.SINGLE);
				return parseFinished();
			}
		}else {
			append(c);
		}
		return Status.PARAM;
	}

	@Override
	protected Status parseBody(char c) {
		if(c == '<') {
			String name = currentDocument.getName();
			if(cut(1 + name.length()).equals("<" + name)) {
				tagCount ++;
			}else if(cut(2 + name.length()).equals("</" + name)) {
				tagCount --;
			}
			if(tagCount == 0) {
				String body = builder.toString();
				currentDocument.setContent(body);
				builder.setLength(0);
				move(2 + name.length());
				return parseFinished();
			}
		}
		append(c);
		return Status.BODY;
	}

	@Override
	protected Status parseFinished() {
		tagCount = 0;
		results.add(currentDocument);
		
		List<Document> childs = new SimpleScanner(currentDocument.getContent()).scan().results();
		if(childs != null && ! childs.isEmpty()) {
			currentDocument.setChilds(childs);
			childs.forEach(doc -> {
				doc.setParent(currentDocument);
			});
		}
		
		return Status.START;
	}
	
	@Override
	public List<Document> results(){
		return results;
	}

	public void quotesFilter(char c) {
		if(c == '\'') {
			if(singleIsClose()) {
				if(doubleIsClose()) {
					singleCount = 1;
				}
			}else {
				singleCount = 0;
			}
		}else if(c == '"') {
			if(doubleIsClose()) {
				if(singleIsClose()) {
					doubleCount = 1;
				}
			}else {
				doubleCount = 0;
			}
		}
	}
	
	public boolean doubleIsClose() {
		return doubleCount == 0;
	}
	
	public boolean singleIsClose() {
		return singleCount == 0;
	}
	
	public boolean quotesIsClose() {
		return doubleIsClose() && singleIsClose();
	}

}
