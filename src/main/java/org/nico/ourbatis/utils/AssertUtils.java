package org.nico.ourbatis.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.nico.ourbatis.exception.EmptyArrayException;
import org.nico.ourbatis.exception.EmptyCollectionException;
import org.nico.ourbatis.exception.EmptyMapException;

public class AssertUtils {

	public static void assertNull(Object o) {
		assertNull(o, "");
	}
	
	public static void assertNull(Object o, String message) {
		if(o == null) {
			throw new NullPointerException(message);
		}
	}
	
	public static void assertBlank(Object o) {
		assertBlank(o, "");
	}
	
	public static void assertBlank(Object o, String message) {
		assertNull(o);
		if(o instanceof Map) {
			if(((Map<?, ?>) o).isEmpty()) {
				throw new EmptyMapException(message);
			}
		}else if(o instanceof Collection) {
			if(((Collection<?>) o).isEmpty()) {
				throw new EmptyCollectionException(message);
			}
		}else if(o.getClass().isArray()) {
			if(Array.getLength(o) == 0) {
				throw new EmptyArrayException(message);
			}
		}
	}
}
