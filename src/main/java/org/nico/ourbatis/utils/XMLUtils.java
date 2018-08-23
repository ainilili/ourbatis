package org.nico.ourbatis.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLUtils {
	
	public static Document createDocument(InputSource inputSource) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(false);
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(false);
			factory.setCoalescing(false);
			factory.setExpandEntityReferences(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setEntityResolver(new XMLMapperEntityResolver());
			builder.setErrorHandler(new ErrorHandler() {
				@Override
				public void error(SAXParseException exception) throws SAXException {
					throw exception;
				}

				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					throw exception;
				}

				@Override
				public void warning(SAXParseException exception) throws SAXException {
				}
			});
			return builder.parse(inputSource);
		} catch (Exception e) {
			throw new BuilderException("Error creating document instance.  Cause: " + e, e);
		}
	}
}
