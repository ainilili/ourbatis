package org.nico.ourbatis.xml;

import java.util.List;
import java.util.Map;

public class XMLDocument {

	private String name;
	
	private XMLDocumentType type;
	
	private Map<String, String> parameters;
	
	private String content;
	
	private String preContent;
	
	private String nextContent;
	
	private XMLDocument preDocument;
	
	private XMLDocument nextDocument;
	
	private XMLDocument parentDocument;
	
	private List<XMLDocument> childDocuments;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public XMLDocumentType getType() {
		return type;
	}

	public void setType(XMLDocumentType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPreContent() {
		return preContent;
	}

	public void setPreContent(String preContent) {
		this.preContent = preContent;
	}

	public String getNextContent() {
		return nextContent;
	}

	public void setNextContent(String nextContent) {
		this.nextContent = nextContent;
	}

	public XMLDocument getPreDocument() {
		return preDocument;
	}

	public void setPreDocument(XMLDocument preDocument) {
		this.preDocument = preDocument;
	}

	public XMLDocument getNextDocument() {
		return nextDocument;
	}

	public void setNextDocument(XMLDocument nextDocument) {
		this.nextDocument = nextDocument;
	}

	public XMLDocument getParentDocument() {
		return parentDocument;
	}

	public void setParentDocument(XMLDocument parentDocument) {
		this.parentDocument = parentDocument;
	}

	public List<XMLDocument> getChildDocuments() {
		return childDocuments;
	}

	public void setChildDocuments(List<XMLDocument> childDocuments) {
		this.childDocuments = childDocuments;
	}
	
}
