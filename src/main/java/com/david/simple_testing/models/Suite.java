package com.david.simple_testing.models;

import java.util.ArrayList;

public class Suite{

	private int id;
	private Project project;
	private String name;
	private String description;
	private ArrayList<InisTest> inisTests;

	public Suite() {
		super();
	}
	
	// This might be good for inserting as the ID is auto incremented in DB
	public Suite(Project project, String name, String description) {
		super();
		this.setId(0);
		this.setProject(project);
		this.setName(name);
		this.setDescription(description);
		inisTests = new ArrayList<>();
	}

	public Suite(int id, Project project, String name, String description) {
		super();
		this.setId(id);
		this.setProject(project);
		this.setName(name);
		this.setDescription(description);
		inisTests = new ArrayList<>();
	}

	public Suite(int id, Project project, String name, String description, ArrayList<InisTest> inisTests) {
		super();
		this.setId(id);
		this.setProject(project);
		this.setName(name);
		this.setDescription(description);
		this.setInisTests(inisTests);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<InisTest> getInisTests() {
		return inisTests;
	}

	public void setInisTests(ArrayList<InisTest> inisTests) {
		this.inisTests = inisTests;
	}

	public void addInisTest(InisTest inisTest) {
		this.inisTests.add(inisTest);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
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
		Suite other = (Suite) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

	public String toJSON() {
		return "{\n  \"suite\":{\n    \"id\": " + id +",\n    \"project_name\": \"" + project.getName() + "\",\n    \"name\": \"" + name + "\",\n    \"description\": \""
				+ description + "\",\n    \"number_of_InisTests\": " + inisTests.size() + "\n  }" + "  \n}";
	}
	
	public String allToJSON() {
		ArrayList<InisTest> allInisTests = this.getInisTests();
		String allInisTestsToJSON = "";
		int i = 1;
		for (InisTest it : allInisTests) {
			allInisTestsToJSON += it.allToJSON();
			System.out.println("allSuites.size() == " + allInisTests.size());
			// Add a comma to the end of a InisTest JSON object unless it's the
			// last one
			if (i++ != allInisTests.size())
				allInisTestsToJSON += ",";
		}
		return "\"suite_" + id + "\":{\"id\": " + id +",\"project_name\": \"" + project.getName() + "\",\"name\": \"" + name + "\",\"description\": \""
		+ description + "\",\"number_of_inis_tests\": " + inisTests.size() + ",\"inis_tests\":{" + allInisTestsToJSON + "}" + "}";
	}
}
