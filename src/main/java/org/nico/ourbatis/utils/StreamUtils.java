package org.nico.ourbatis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.ibatis.io.Resources;

public class StreamUtils {
	
	/**
	 * Convert input stream to string
	 * 
	 * @param inStream Input stream
	 * @return {@link String}
	 * @throws IOException If an I/O error occurs
	 */
	public static String convertToString(InputStream inStream){ 
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));  
		StringBuilder reqStr = new StringBuilder();  
		char[] buf = new char[2048];  
		int len = -1;
		try {
			while ((len = br.read(buf)) != -1) {  
				reqStr.append(new String(buf, 0, len));  
			}  
			br.close();
		}catch(IOException e) {
			return null;
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reqStr.toString();
	}
	
	public static String convertToString(String resource){ 
		try {
			return convertToString(Resources.getResourceAsStream(resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
