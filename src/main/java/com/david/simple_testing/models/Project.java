package com.david.simple_testing.models;

import java.util.ArrayList;

public class Project {

	private int id;
	private String name;
	private String createdOn;
	private ArrayList<Suite> suites;

	public Project() {
		super();
	}

	// This might be good for inserting as the ID is auto incremented in DB
	public Project(String name, String createdOn) {
		super();
		this.setId(0);
		this.setName(name);
		this.setCreatedOn(createdOn);
		suites = new ArrayList<>();
	}

	public Project(int id, String name, String createdOn) {
		super();
		this.setId(id);
		this.setName(name);
		this.setCreatedOn(createdOn);
		suites = new ArrayList<>();
	}

	public Project(int id, String name, String createdOn, ArrayList<Suite> suites) {
		super();
		this.setId(id);
		this.setName(name);
		this.setCreatedOn(createdOn);
		this.setSuites(suites);
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

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public ArrayList<Suite> getSuites() {
		return suites;
	}

	public void setSuites(ArrayList<Suite> suites) {
		this.suites = suites;
	}

	public void addSuite(Suite suite) {
		this.suites.add(suite);
	}

	public String toJSON() {
		return "{\n  \"project\":{\n    \"id\": " + id + ",\n    \"name\": \"" + name + "\",\n    \"created_on\": \""
				+ createdOn + "\",\n    \"number_of_suites\":" + suites.size() + "\n  }" + "  \n}";
	}

	public String allToJSON() {
		// Get all the Suites related to this project
		// Gets them in JSON format
		ArrayList<Suite> allSuites = this.getSuites();
		String allSuitesToJSON = "";
		int i = 1;
		for (Suite s : allSuites) {
			allSuitesToJSON += s.allToJSON();
			System.out.println("allSuites.size() == " + allSuites.size());
			// Add a comma to the end of a suite JSON object unless it's the
			// last one
			if (i++ != allSuites.size())
				allSuitesToJSON += ",";
		}

		return "{\"id\": " + id + ",\"name\": \"" + name + "\",\"created_on\": \"" + createdOn
				+ "\",\"number_of_suites\":" + suites.size() + ",\"suites\":{" + allSuitesToJSON + "}" + "}";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
