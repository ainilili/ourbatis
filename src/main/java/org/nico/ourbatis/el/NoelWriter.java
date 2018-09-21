package org.nico.ourbatis.el;

import java.util.List;
import java.util.function.Consumer;

import org.nico.ourbatis.config.BaseConfig;
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
	
	public NoelWriter write(Consumer<DomBean> specialCallBack){
		write(this.documents, specialCallBack);
		return this;
	}
	
	private NoelWriter write(List<DomBean> documents, Consumer<DomBean> specialCallBack){
		for(DomBean d: documents){
			if(BaseConfig.targets.contains(d.getPrefix())) {
				specialCallBack.accept(d);
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
