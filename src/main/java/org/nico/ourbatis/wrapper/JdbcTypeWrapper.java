package org.nico.ourbatis.wrapper;

import org.nico.ourbatis.OurBatis;

/** 
 * 
 * @author nico
 * @version createTime：2018年8月26日 下午5:05:32
 */

public class JdbcTypeWrapper extends Wrapper<Class<?>>{
	
	@Override
	public String wrapping(Class<?> key) {
		return OurBatis.JAVA_TYPE_MAPPER.get(key);
	}

}
