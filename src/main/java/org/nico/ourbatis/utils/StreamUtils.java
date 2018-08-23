package org.nico.ourbatis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtils {
	
	/**
	 * Convert input stream to string
	 * 
	 * @param inStream Input stream
	 * @return {@link String}
	 * @throws IOException If an I/O error occurs
	 */
	public static byte[] convertToString(InputStream inStream) throws IOException  { 
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));  
		StringBuilder reqStr = new StringBuilder();  
		char[] buf = new char[2048];  
		int len = -1;
		while ((len = br.read(buf)) != -1) {  
			reqStr.append(new String(buf, 0, len));  
		}  
		br.close();
		return reqStr.toString().getBytes();
	}
	
}
