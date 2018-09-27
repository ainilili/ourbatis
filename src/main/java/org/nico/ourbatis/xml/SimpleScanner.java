package org.nico.ourbatis.xml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nico.noson.util.string.StringUtils;

public class SimpleScanner extends SmartScanner{

	private Document currentDocument;
	
	private LinkedList<Document> results;
	
	private int singleCount = 0;
	
	private int doubleCount = 0;
	
	private int tagCount = 0;
	
	public static final Map<String, String> SPECIAL_NAMES = new HashMap<String, String>(){
		private static final long serialVersionUID = 6675296460361731643L;
		{
			put("?xml", "?");
			put("!--", "--");
		}
	};
	
	public static final Set<String> NOTAIL_NAMES = new HashSet<String>(){
		private static final long serialVersionUID = 6675296460361731643L;
		{
			add("!DOCTYPE");
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
		if(c == ' ' || c == '\t' || c == '>' || cut(2).equals("/>") || (SPECIAL_NAMES.containsKey(builder.toString()) && cut(SPECIAL_NAMES.get(builder.toString()).length()).equals(SPECIAL_NAMES.get(builder.toString())))) {
			String name = builder.toString();
			currentDocument.setName(name);
			tagCount ++;
			builder.setLength(0);
			if(c == ' ') {
				return Status.PARAM;
			}else if(NOTAIL_NAMES.contains(currentDocument.getName())) {
				currentDocument.setType(DocumentType.SINGLE);
				currentDocument.setTail("");
				return parseFinished();
			}else if(c == '>') {
				currentDocument.setType(DocumentType.DOUBLE);
				return Status.BODY;
			}else if(cut(2).equals("/>")){
				currentDocument.setType(DocumentType.SINGLE);
				currentDocument.setTail("/");
				return Status.BODY;
			}else{
				currentDocument.setType(DocumentType.SINGLE);
				currentDocument.setTail(SPECIAL_NAMES.get(currentDocument.getName()));
				move(currentDocument.getTail().length());
				return parseFinished();
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
		if(quotesIsClose() && (c == '>' || cut(2).equals("/>") || (SPECIAL_NAMES.containsKey(currentDocument.getName()) && cut(SPECIAL_NAMES.get(currentDocument.getName()).length()).equals(SPECIAL_NAMES.get(currentDocument.getName()))))) {
			String params = builder.toString();
			currentDocument.setParameterString(params);
			currentDocument.setParameters(DocumentUtils.parseParameters(params));
			builder.setLength(0);
			if(NOTAIL_NAMES.contains(currentDocument.getName())) {
				currentDocument.setType(DocumentType.SINGLE);
				currentDocument.setTail("");
				return parseFinished();
			}else if(c == '>') {
				currentDocument.setType(DocumentType.DOUBLE);
				return Status.BODY;
			}else if(cut(2).equals("/>")){
				move(1);
				currentDocument.setType(DocumentType.SINGLE);
				currentDocument.setTail("/");
				return parseFinished();
			}else {
				currentDocument.setType(DocumentType.SINGLE);
				currentDocument.setTail(cut(SPECIAL_NAMES.get(currentDocument.getName()).length()));
				move(currentDocument.getTail().length());
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
