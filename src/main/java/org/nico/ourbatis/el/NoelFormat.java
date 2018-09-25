package org.nico.ourbatis.el;

import java.util.List;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.scan.impl.NicoScanner;

public class NoelFormat {

	private String document;
	
	private StringBuilder builder = new StringBuilder();
	
	public NoelFormat(String document) {
		this.document = document;
	}

	public void append(String body) {
		this.builder.append(body);
	}
	
	public String result() {
		return builder.toString();
	}
	
	public NoelFormat format(){
		format(new NicoScanner(document).getDomBeans(), 0);
		return this;
	}
	
	private NoelFormat format(List<DomBean> documents, int n){
		StringBuilder tab = new StringBuilder();
		int tn = n;
		while(tn -- > 0) tab.append("\t");
		for(DomBean d: documents){
				String paramStr = "";
				if(d.getParamStr() != null){
					paramStr = d.getParamStr().replaceAll("&", " ");
				}
				if(d.isSelfSealing()){
					builder.append(tab);
					builder.append("<" + d.getPrefix() + " " + paramStr + " />");
					builder.append(System.lineSeparator());
				}else{
					builder.append(tab);
					builder.append("<" + d.getPrefix() + " " + paramStr + " >");
					builder.append(System.lineSeparator());
					if(d.getDomProcessers() != null && d.getDomProcessers().size() > 0){
						format(d.getDomProcessers(), n + 1);
					}else{
						builder.append(tab);
						builder.append(d.getBody());
						builder.append(System.lineSeparator());
					}
					builder.append(tab);
					builder.append("</"+ d.getPrefix() + ">");
					builder.append(System.lineSeparator());
				}
		}
		return this;
	}
	
}
