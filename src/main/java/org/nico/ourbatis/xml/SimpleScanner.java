package org.nico.ourbatis.xml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A scanner with a simple implementation can complete parsing of hypertext. 
 * Each scan of this class will fetch a hypertext object whose child elements
 *  can be retrieved recursively, customizing the definition of the special 
 *  label {SPECIAL_NAMES, NOTAIL_NAMES} for complex parsing situations.
 * 
 * @author nico
 */
public class SimpleScanner extends SmartScanner{

private Document currentDocument;
	
	private LinkedList<Document> results;
	
	private int singleCount = 0;
	
	private int doubleCount = 0;
	
	private int tagCount = 0;
	
	/**
	 * Define label pairs that are not consistent between open and closed labels
	 */
	public static final Map<String, String> SPECIAL_NAMES = new HashMap<String, String>(){
		private static final long serialVersionUID = 6675296460361731643L;
		{
			put("?xml", "?");
			put("!--", "--");
		}
	};
	
	/**
	 * A tag that defines an open tag
	 */
	public static final Set<String> NOTAIL_NAMES = new HashSet<String>(){
		private static final long serialVersionUID = 6675296460361731643L;
		{
			add("!DOCTYPE");
			add("img");
		}
	};
	
	public SimpleScanner(String value) {
		super(value);
		results = new LinkedList<Document>();
	}

	@Override
	protected Status parseStart(char c) {
		if(c == '<' && ! cut(2).equals("</")) {
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
		String special = SPECIAL_NAMES.get(builder.toString());
		if(c == ' ' || c == '\t' || c == '\n' || c == '>' || cut(2).equals("/>") || (special != null && cut(special.length()).equals(special))) {
			String name = builder.toString();
			if(c == '\n') {
				name = name.replaceAll("\r", "");
			}
			currentDocument.setName(name);
			tagCount ++;
			builder.setLength(0);
			if(c == ' ' || c == '\t' || c == '\n') {
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
				move(1);
				return parseFinished();
			}else{
				currentDocument.setType(DocumentType.SINGLE);
				currentDocument.setTail(special);
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
		String special = SPECIAL_NAMES.get(currentDocument.getName());
		if(quotesIsClose() && (c == '>' || cut(2).equals("/>") || (special != null && cut(special.length()).equals(special)))) {
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
				currentDocument.setTail(cut(special.length()));
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
			if(cut(1 + name.length()).equals("<" + name) && ! isClose()) {
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
