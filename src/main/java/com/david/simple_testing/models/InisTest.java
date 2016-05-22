package com.david.simple_testing.models;

import java.util.ArrayList;

public class InisTest {

	private int id;
	private Suite suite;
	private Browser browser;
	private String name;
	private String description;
	private ArrayList<Step> steps;

	public InisTest() {

	}

	// This might be good for inserting as the ID is auto incremented in DB
	public InisTest(Suite suite, String name, Browser browser, String description) {
		super();
		this.setId(0);
		this.setSuite(suite);
		this.setName(name);
		this.setBrowser(browser);
		this.setDescription(description);
		this.steps = new ArrayList<>();
	}

	public InisTest(int id, Suite suite, String name, Browser browser, String description) {
		super();
		this.setId(id);
		this.setSuite(suite);
		this.setName(name);
		this.setBrowser(browser);
		this.setDescription(description);
		this.steps = new ArrayList<>();
	}

	public InisTest(int id, Suite suite, String name, String description, ArrayList<Step> steps) {
		super();
		this.setId(id);
		this.setSuite(suite);
		this.setName(name);
		this.setDescription(description);
		this.setSteps(steps);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Suite getSuite() {
		return suite;
	}

	public void setSuite(Suite suite) {
		this.suite = suite;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
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

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}

	public void addStep(Step step) {
		this.steps.add(step);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((suite == null) ? 0 : suite.hashCode());
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
		InisTest other = (InisTest) obj;
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
		if (suite == null) {
			if (other.suite != null)
				return false;
		} else if (!suite.equals(other.suite))
			return false;
		return true;
	}

	public String toJSON() {
		return "{\n  \"inis_test\":{\n    \"id\": " + id + ",\n    \"suite\": \"" + suite.getName()
				+ "\",\n    \"name\": \"" + name + "\",\n    \"description\": \"" + description
				+ "\",\n    \"number_of_steps\": " + steps.size() + "\n  }" + "  \n}";
	}
	
	public String allToJSON() {
		ArrayList<Step> allSteps = this.getSteps();
		String allStepsToJSON = "";
		int i = 1;
		for (Step s : allSteps) {
			allStepsToJSON += s.allToJSON();
			System.out.println("allSuites.size() == " + allSteps.size());
			// Add a comma to the end of a Step JSON object unless it's the
			// last one
			if (i++ != allSteps.size())
				allStepsToJSON += ",";
		}
		return "\"inis_test_" + id + "\":{\"id\": " + id +",\"suite\": \"" + suite.getName() + "\",\"name\": \"" + name + "\",\"description\": \""
		+ description + "\",\"number_of_steps\": " + steps.size() + ",\"steps\":{" + allStepsToJSON + "}" + "}";
	}
}
