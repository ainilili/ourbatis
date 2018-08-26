package org.nico.test;

import org.nico.ourbatis.annotation.RenderPrimary;

public class TestEntity {

	@RenderPrimary
	private String id;
	
	private Integer age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
