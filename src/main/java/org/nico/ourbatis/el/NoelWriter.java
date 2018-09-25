package org.nico.ourbatis.el;

import java.util.List;
import java.util.function.Function;

import org.nico.ourbatis.adapter.AssistAdapter;
import org.nico.ourbatis.config.OurConfig;
import org.nico.seeker.dom.DomBean;

public class NoelWriter {

	private List<DomBean> documents;
	
	private StringBuilder builder = new StringBuilder();
	
	public NoelWriter(List<DomBean> documents) {
		this.documents = documents;
	}
	
	public void append(String body) {
		this.builder.append(body);
	}
	
	public String body() {
		return builder.toString();
	}
	
	public NoelWriter write(Function<DomBean, String> specialCallBack, Function<String, String> thenCallBack){
		write(this.documents, specialCallBack);
		this.builder = new StringBuilder(thenCallBack.apply(body()));
		return this;
	}
	
	public NoelWriter writeProperties(AssistAdapter adapter){
		
		return this;
	}
	
	public String format() {
		return new NoelFormat(body())
				.format()
				.result();
	}
	
	private NoelWriter write(List<DomBean> documents, Function<DomBean, String> specialCallBack){
		for(DomBean d: documents){
			if(OurConfig.adapterMap.containsKey(d.getPrefix())) {
				builder.append(specialCallBack.apply(d));
			}else {
				String paramStr = "";
				if(d.getParamStr() != null){
					paramStr = d.getParamStr().replaceAll("&", " ");
				}
				if(d.isSelfSealing()){
					builder.append("<" + d.getPrefix() + " " + paramStr + " />");
				}else{
					builder.append("<" + d.getPrefix() + " " + paramStr + " >");
					if(d.getDomProcessers() != null && d.getDomProcessers().size() > 0){
						write(d.getDomProcessers(), specialCallBack);
					}else{
						builder.append(d.getBody());
					}
					builder.append("</"+ d.getPrefix() + ">");
				}
				
			}
		}
		return this;
	}
	
}
