package org.nico.ourbatis.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.noson.util.string.StringUtils;

public class SimpleScanner extends SmartScanner{

	private XMLDocument currentDocument;
	
	private List<XMLDocument> results;
	
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
		results = new ArrayList<XMLDocument>();
	}

	@Override
	protected Status parseStart(char c) {
		if(c == '<') {
			currentDocument = new XMLDocument();
			String preContent = builder.toString();
			currentDocument.setPreContent(preContent);
			builder.setLength(0);
			return Status.HEAD;
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
				currentDocument.setType(XMLDocumentType.DOUBLE);
				return Status.BODY;
			}else if(cut(2).equals("/>")){
				currentDocument.setType(XMLDocumentType.SINGLE);
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
			return Status.START;
		}
		return Status.ANNOTATION;
	}

	@Override
	protected Status parseParam(char c) {
		quotesFilter(c);
		if(quotesIsClose() && (c == '>' || cut(2).equals("/>") || (specialNames.containsKey(currentDocument.getName()) && cut(1).equals(specialNames.get(currentDocument.getName()))))) {
			String params = builder.toString();
			currentDocument.setParameters(parseParameters(params));
			builder.setLength(0);
			if(c == '>') {
				currentDocument.setType(XMLDocumentType.DOUBLE);
				return Status.BODY;
			}else if(cut(2).equals("/>")){
				move(1);
				currentDocument.setType(XMLDocumentType.SINGLE);
				return Status.START;
			}else {
				currentDocument.setType(XMLDocumentType.SINGLE);
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
		currentDocument.setChildDocuments(new SimpleScanner(currentDocument.getContent()).scan().results());
		return Status.START;
	}
	
	@Override
	public List<XMLDocument> results(){
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
	
	public Map<String, String> parseParameters(String paramStr) {
		Map<String, String> parameters = new HashMap<String, String>();
		if(StringUtils.isNotBlank(paramStr));
		char[] domChars = paramStr.toCharArray();
		String[] kv = new String[2];
		StringBuffer cache = new StringBuffer();
		int singleCount = 0;
		int doubleCount = 0;
		boolean scanValue = false;
		for(int index = 0; index < domChars.length; index++){
			char c = domChars[index];
			if(index == domChars.length - 1){
				if(scanValue){
					kv[1] = cache.toString();
					parameters.put(kv[0], kv[1]);
				}
			}else{
				if(doubleCount == 0 && c == '\''){
					singleCount = singleCount == 1 ? 0 : 1;
					continue;
				}
				if(singleCount == 0 && c == '"'){
					doubleCount = doubleCount == 1 ? 0 : 1;
					continue;
				}
				if(c == '\r' || c == '\n') continue;
				if(scanValue){
					if(singleCount == 1 || doubleCount == 1){
						cache.append(c);
					}
					if(cache.length() != 0 && singleCount == 0 && doubleCount == 0){
						kv[1] = cache.toString();
						cache.setLength(0);
						parameters.put(kv[0], kv[1]);
						scanValue = false;
					}
				}else{
					if(c == '='){
						kv[0] = cache.toString().trim();
						cache.setLength(0);
						scanValue = true;
						continue;
					}else{
						cache.append(c);	
					}
				}
			}
		}
		return parameters;
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
