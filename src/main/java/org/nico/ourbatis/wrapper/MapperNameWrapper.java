package org.nico.ourbatis.wrapper;

import java.util.List;

import org.nico.ourbatis.Ourbatis;

public class MapperNameWrapper implements Wrapper<Class<?>>{

	@Override
	public String wrapping(Class<?> domainClass) {
		String tableName = domainClass.getSimpleName();
		List<Wrapper<String>> wrappers = Ourbatis.MAPPER_NAME_WRAPPERS;
		if(wrappers == null) {
			throw new NullPointerException("Wrappers is null");
		}
		for(Wrapper<String> wrapper: wrappers){
			tableName = wrapper.wrapping(tableName);
		}
		return tableName;
	}

}
