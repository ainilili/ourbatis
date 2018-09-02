package org.nico.test;

import java.util.Date;

import org.nico.ourbatis.annotation.RenderPrimary;

public class TestEntity {

	@RenderPrimary
	private String id;
	
	private Integer age;
	
	private Date date;
	
	private float price;
	
	private double db;
	
	private byte bs;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public double getDb() {
		return db;
	}

	public void setDb(double db) {
		this.db = db;
	}

	public byte getBs() {
		return bs;
	}

	public void setBs(byte bs) {
		this.bs = bs;
	}

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
