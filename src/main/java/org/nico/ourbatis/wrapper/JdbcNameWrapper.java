package org.nico.ourbatis.wrapper;

import java.lang.reflect.Field;
import java.util.List;

import org.nico.ourbatis.OurBatis;
import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.utils.ReflactUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年8月26日 下午5:05:32
 */

public class JdbcNameWrapper extends Wrapper<Field>{

	@Override
	public String wrapping(Field field) {
		String fieldName = field.getName();
		boolean tableRenderFlag = true;
		if(ReflactUtils.isRenderName(field)){
			fieldName = ReflactUtils.getRenderName(field);
			if(! ReflactUtils.getAnnotation(field, RenderName.class).render()){
				tableRenderFlag = false;
			}
		}
		if(tableRenderFlag){
			List<Wrapper<String>> wrappers = OurBatis.JDBC_NAME_WRAPPERS;
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
