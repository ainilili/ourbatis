package org.nico.ourbatis.wrapper;

import java.lang.reflect.Field;
import java.util.List;

import org.nico.ourbatis.Ourbatis;
import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.utils.ReflectUtils;

public class JdbcNameWrapper implements Wrapper<Field>{

	@Override
	public String wrapping(Field field) {
		String fieldName = field.getName();
		boolean tableRenderFlag = true;
		if(ReflectUtils.isRenderName(field)){
			fieldName = ReflectUtils.getRenderName(field);
			if(! ReflectUtils.getAnnotation(field, RenderName.class).render()){
				tableRenderFlag = false;
			}
		}
		if(tableRenderFlag){
			List<Wrapper<String>> wrappers = Ourbatis.JDBC_NAME_WRAPPERS;
			if(wrappers == null) {
				throw new NullPointerException("Wrappers is null");
			}
			for(Wrapper<String> wrapper: wrappers){
				fieldName = wrapper.wrapping(fieldName);
			}
		}
		return fieldName;
	}

}
