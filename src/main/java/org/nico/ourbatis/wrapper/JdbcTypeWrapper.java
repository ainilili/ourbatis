package org.nico.ourbatis.wrapper;

import org.nico.ourbatis.Ourbatis;

public class JdbcTypeWrapper implements Wrapper<Class<?>>{
	
	@Override
	public String wrapping(Class<?> key) {
		return Ourbatis.JAVA_TYPE_MAPPER.get(key);
	}

}
