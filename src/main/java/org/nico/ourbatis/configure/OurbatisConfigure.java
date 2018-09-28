package org.nico.ourbatis.configure;

import java.util.List;
import java.util.Map;

import org.nico.ourbatis.adapter.AssistAdapter;
import org.nico.ourbatis.wrapper.Wrapper;

public interface OurbatisConfigure {

	public void configWrapper(
			List<Wrapper<String>> JDBC_NAME_WRAPPERS,
			List<Wrapper<String>> TABLE_NAME_WRAPPERS,
			List<Wrapper<String>> MAPPER_NAME_WRAPPERS,
			Map<Class<?>, String> JAVA_TYPE_WRAPPERS
			);
	
	public void configAdapter(
			Map<String, AssistAdapter> ASSIST_ADAPTERS
			);
	
}
