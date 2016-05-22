package com.david.simple_testing.databaseTests;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.database.ReadDatabase;
import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

public class ReadDataFromDatabaseWithAbstract {

	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";

	ReadDatabase dbc;
	Project returnedProject = null;
	ArrayList<Suite> returnedSuites;
	ArrayList<InisTest> returnedTests;

	@BeforeClass
	public void setup() {
		dbc = new ReadDatabase();
	}

	@AfterClass
	public void testCleanup() {
		System.out.println("\n\nEntered the test clean up...");
		dbc.disconnect();
		System.out.println("======================Finished test " + this.getClass().getSimpleName() + "======================");
	}

	@Test
	public void testConnectToDatabase() {
		dbc.connect(IP, DATABASE, USERNAME, PASSWORD);

	}

	@Test(dependsOnMethods = { "testConnectToDatabase" })
	public void testDatabaseReadProjectById() throws SQLException {
		System.out.println("\n\nEntered the test testDatabaseReadProjectById...");
		int projectId = 1;

		returnedProject = dbc.readProjectById(projectId);
		System.out.println("Returned Project: " + returnedProject.allToJSON());

		Assert.assertTrue(returnedProject != null);
	}

	@Test(dependsOnMethods = { "testDatabaseReadProjectById" })
	public void testDatabaseReadSuitesByProject() throws SQLException {
		System.out.println("\n\nEntered the test testDatabaseReadProjectById...");
		returnedSuites = new ArrayList<>();

		returnedSuites = dbc.readAllSuitesByProject(returnedProject);
		System.out.println("Returned Suites: " + returnedSuites);

		Assert.assertTrue(!returnedSuites.isEmpty());
		for (Suite s : returnedSuites) {
			returnedProject.addSuite(s);
		}
	}

	@Test(dependsOnMethods = { "testDatabaseReadSuitesByProject" })
	public void testDatabaseReadTestsBySuite() throws SQLException {
		System.out.println("\n\nEntered the test testDatabaseReadTestsBySuite...");
		returnedTests = new ArrayList<>();

		for (Suite s : returnedSuites) {
			returnedTests = dbc.readAllTestsBySuite(s);
			System.out.println("There are " + returnedTests.size() + " tests in suite " + s.getName());

			// Test that there is one test in each Suite
			Assert.assertTrue(returnedTests.size() == 1 || returnedTests.size() == 2, "Returned Tests.size() = " + returnedTests.size() + " != " + 2);
			s.setInisTests(returnedTests);

		}
	}

	@Test(dependsOnMethods = { "testDatabaseReadTestsBySuite" })
	public void testDatabaseReadStepsByTest() throws SQLException {
		System.out.println("\n\nEntered the test testDatabaseReadTestsBySuite...");
		ArrayList<Step> returnedSteps = new ArrayList<>();

		for (InisTest t : returnedTests) {
			returnedSteps = dbc.readAllStepsByTest(t);
			System.out.println("There are " + returnedSteps.size() + " steps in test" + t.getName());

			// Test that there is 4 steps in each InisTest
			Assert.assertTrue(returnedSteps.size() == 4);

			for (Step s : returnedSteps) {
				System.out.println(s.toString());
			}
		}
	}

	@Test(dependsOnMethods = { "testDatabaseReadStepsByTest" })
	public void testDatabaseReadAllProjectData() throws SQLException {
		System.out.println("\n\nEntered the test testDatabaseReadAllProjectData...");
		System.out.println("The whole lot of info for project:\n");
		System.out.println(dbc.getAllProjectData(1).toString());
	}

}
