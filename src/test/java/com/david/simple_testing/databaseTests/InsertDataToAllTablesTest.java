package com.david.simple_testing.databaseTests;

import java.util.ArrayList;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.database.DeleteDatabase;
import com.david.simple_testing.database.InsertDatabase;
import com.david.simple_testing.models.Browser;
import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

/*
 * This test will create new Project, Suite, InisTest, and Step objects and store them in the DB. 
 * It will then DELETE the Data again by using the POJOs
*/
public class InsertDataToAllTablesTest {

	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";

	InsertDatabase dbc;
	DeleteDatabase ddbc;
	Project project = null;
	Suite suite = null;
	InisTest inisTest = null;
	Step step = null;
	Browser browser = null;

	ArrayList<Suite> suites;
	ArrayList<InisTest> inisTests;
	ArrayList<Step> steps;

	@BeforeClass
	public void setup() {
		System.out.println("\n\nEntered the test setup...");

		dbc = new InsertDatabase();
		ddbc = new DeleteDatabase();
		// Create the POJOs needed
		browser = new Browser(1);
		project = new Project("Project From test", "17/05/16");
		suite = new Suite(project, "Suite From Test", "This is generated from a test. Should be deleted");
		inisTest = new InisTest(suite, "InisTest from Test", browser,
				"This is generated from a test. Should be deleted");
		step = new Step(inisTest, "Go to URL", "Yabba Dabba Dooo", "Scooby Snacks");
	}

	@AfterClass
	public void testCleanup() {
		System.out.println("\n\nEntered the test clean up...");

		dbc.disconnect();
		ddbc.disconnect();
		System.out.println(
				"======================Finished test " + this.getClass().getSimpleName() + "======================");
	}

	@Test
	public void testConnectToDatabase() {
		System.out.println("\n\nEntered the testConnectToDatabase...");

		dbc.connect(IP, DATABASE, USERNAME, PASSWORD);
	}

	@Test(dependsOnMethods = { "testConnectToDatabase" })
	public void testProjectInsert() {
		System.out.println("\n\nEntered the testProjectInsert...");

		project.setId(dbc.insertProject(project));
	}

	@Test(dependsOnMethods = { "testProjectInsert" })
	public void testSuiteInsert() {
		System.out.println("\n\nEntered the testSuiteInsert...");

		suite.setId(dbc.insertSuite(suite));
		project.addSuite(suite);
	}

	@Test(dependsOnMethods = { "testSuiteInsert" })
	public void testInisTestInsert() {
		System.out.println("\n\nEntered the testInisTestInsert...");

		inisTest.setId(dbc.insertInisTest(inisTest));
		suite.addInisTest(inisTest);
	}

	@Test(dependsOnMethods = { "testInisTestInsert" })
	public void testStepInsert() {
		System.out.println("\n\nEntered the testStepInsert...");

		step.setId(dbc.insertStep(step));
		inisTest.addStep(step);
	}

	@Test(dependsOnMethods = { "testStepInsert" })
	public void testAllProjectDataDelete() {
		System.out.println("\n\nEntered the testProjectDelete...");

		ddbc.connect(IP, DATABASE, USERNAME, PASSWORD);
		AssertJUnit.assertTrue(ddbc.deleteAllProjectData(project));
	}
}
