package org.nico.ourbatis.wrapper;

import java.util.List;

import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.contains.OurConnfig;
import org.nico.ourbatis.utils.ReflactUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年8月26日 下午5:05:32
 */

public class TableNameWrapper extends Wrapper<Class<?>>{

	@Override
	public String wrapping(Class<?> domainClass) {
		String tableName = domainClass.getSimpleName();
		boolean tableRenderFlag = true;
		if(ReflactUtils.isRenderName(domainClass)){
			tableName = ReflactUtils.getRenderName(domainClass);
			if(! ReflactUtils.getAnnotation(domainClass, RenderName.class).render()){
				tableRenderFlag = false;
			}
		}
		if(tableRenderFlag){
			List<Wrapper<String>> wrappers = OurConnfig.TABLE_NAME_WRAPPERS;
			if(wrappers == null) {
				throw new NullPointerException("Wrappers is null");
			}
			for(Wrapper<String> wrapper: wrappers){
				tableName = wrapper.wrapping(tableName);
			}
		}
		return tableName;
	}

}
