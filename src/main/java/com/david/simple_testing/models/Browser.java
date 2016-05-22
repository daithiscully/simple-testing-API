package com.david.simple_testing.models;

public class Browser {

	private int id;
	private String name;
	private String version;

	public Browser(int id) {
		super();
		this.id = id;
	}

	public Browser(int id, String name, String version) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
