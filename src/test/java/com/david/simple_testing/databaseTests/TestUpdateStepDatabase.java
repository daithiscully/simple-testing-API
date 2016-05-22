package com.david.simple_testing.databaseTests;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.database.ReadDatabase;
import com.david.simple_testing.database.UpdateDatabase;
import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

public class TestUpdateStepDatabase {

	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";

	private final int PROJECT_ID = 1;
	private final String STEP_NEW_ACTION = "I am a new step";
	private final String STEP_NEW_ACTION_DATA_1 = "Go to a url ssd";
	private final String STEP_NEW_ACTION_DATA_2 = "Clear cache";

	Step step;
	InisTest inisTest;
	Suite suite;
	Project project;
	ReadDatabase rdb;
	UpdateDatabase udb;

	@BeforeClass
	public void setup() throws SQLException {
		rdb = new ReadDatabase();
		udb = new UpdateDatabase();
		rdb.connect(IP, DATABASE, USERNAME, PASSWORD);
		udb.connect(IP, DATABASE, USERNAME, PASSWORD);

		project = rdb.readProjectById(PROJECT_ID);
		suite = rdb.readAllSuitesByProject(project).get(0);
		inisTest = suite.getInisTests().get(0);
		step = inisTest.getSteps().get(0);
	}

	@Test
	public void updateStep() {
		step.setAction(STEP_NEW_ACTION);
		step.setActionData1(STEP_NEW_ACTION_DATA_1);
		step.setActionData2(STEP_NEW_ACTION_DATA_2);

		udb.updateStep(step);
	}

	@Test(dependsOnMethods = { "updateStep" })
	public void isStepUpdated() throws SQLException {
		step = rdb.readAllStepsByTest(inisTest).get(0);

		Assert.assertTrue(step.getAction().equals(STEP_NEW_ACTION),
				"ERROR: Step action " + step.getAction() + " in DB is != " + STEP_NEW_ACTION);
		Assert.assertTrue(step.getActionData1().equals(STEP_NEW_ACTION_DATA_1), "ERROR: Step Action Data 1 "
				+ step.getActionData1() + " in DB is != " + STEP_NEW_ACTION_DATA_1);
		Assert.assertTrue(step.getActionData2().equals(STEP_NEW_ACTION_DATA_2), "ERROR: Step Action Data 2 "
				+ step.getActionData2() + " in DB is != " + STEP_NEW_ACTION_DATA_2);
	}

	@AfterClass
	public void teardown() {
		rdb.disconnect();
		udb.disconnect();
		System.out.println(
				"======================Finished test " + this.getClass().getSimpleName() + "======================");
	}

}
