package org.nico.ourbatis.configure;

import java.util.List;
import java.util.Map;

import org.nico.ourbatis.adapter.AssistAdapter;
import org.nico.ourbatis.wrapper.Wrapper;
import org.nico.ourbatis.wrapper.simple.FieldEscapeWrapper;
import org.nico.ourbatis.wrapper.simple.SlideBarJointWrapper;

public class OurbatisDefaultConfigue implements OurbatisConfigure{

	protected SlideBarJointWrapper slideBarJointWrapper = new SlideBarJointWrapper();
	
	protected FieldEscapeWrapper fieldEscapeWrapper = new FieldEscapeWrapper();
	
	@Override
	public void configWrapper(List<Wrapper<String>> JDBC_NAME_WRAPPERS, List<Wrapper<String>> TABLE_NAME_WRAPPERS,
			List<Wrapper<String>> MAPPER_NAME_WRAPPERS, Map<Class<?>, String> JAVA_TYPE_WRAPPERS) {
		TABLE_NAME_WRAPPERS.add(slideBarJointWrapper);
		JDBC_NAME_WRAPPERS.add(slideBarJointWrapper);
		MAPPER_NAME_WRAPPERS.add(value -> {return value + "Mapper";});
	}

	@Override
	public void configAdapter(Map<String, AssistAdapter> ASSIST_ADAPTERS) {
	}

}
