package com.david.simple_testing.models;

public class Step extends InisTest {

	private int id;
	private InisTest test;
	private String action;
	private String actionData1;
	private String actionData2;

	public Step() {
		super();
	}

	public Step(InisTest test, String action, String actionData1, String actionData2) {
		super();
		this.setId(0);
		this.setTest(test);
		this.setAction(action);
		this.setActionData1(actionData1);
		this.setActionData2(actionData2);
	}

	public Step(int id, InisTest test, String action, String actionData1, String actionData2) {
		super();
		this.id = id;
		this.test = test;
		this.action = action;
		this.actionData1 = actionData1;
		this.actionData2 = actionData2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InisTest getInisTest() {
		return test;
	}

	public void setTest(InisTest test) {
		this.test = test;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionData1() {
		return actionData1;
	}

	public void setActionData1(String actionData1) {
		this.actionData1 = actionData1;
	}

	public String getActionData2() {
		return actionData2;
	}

	public void setActionData2(String actionData2) {
		this.actionData2 = actionData2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((actionData1 == null) ? 0 : actionData1.hashCode());
		result = prime * result + ((actionData2 == null) ? 0 : actionData2.hashCode());
		result = prime * result + id;
		result = prime * result + ((test == null) ? 0 : test.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (actionData1 == null) {
			if (other.actionData1 != null)
				return false;
		} else if (!actionData1.equals(other.actionData1))
			return false;
		if (actionData2 == null) {
			if (other.actionData2 != null)
				return false;
		} else if (!actionData2.equals(other.actionData2))
			return false;
		if (id != other.id)
			return false;
		if (test == null) {
			if (other.test != null)
				return false;
		} else if (!test.equals(other.test))
			return false;
		return true;
	}

	public String toJSON() {
		return "{\n  \"step\":{\n    \"id\": " + id + ",\n    \"test\": \"" + test.getName() + "\",\n    \"action\": \""
				+ action + "\",\n    \"actionData1\": \"" + actionData1 + "\",\n    \"actionData2\": \"" + actionData2
				+ "\"\n  }" + "  \n}";
	}

	public String allToJSON() {

		return "\"step_" + id + "\":{\"id\": " + id + ",\"test_name\": \"" + test.getName() + "\",\"action\": \"" + action
				+ "\",\"actionData1\": \"" + actionData1 + "\",\"actionData2\": \"" + actionData2 + "\"}";
	}

}
