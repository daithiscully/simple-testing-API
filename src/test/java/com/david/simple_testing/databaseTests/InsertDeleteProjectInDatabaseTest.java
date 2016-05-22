package com.david.simple_testing.databaseTests;

import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.database.DeleteDatabase;
import com.david.simple_testing.database.InsertDatabase;
import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Suite;

/*
 * This test will create a new Project object and store it in the DB. 
 * It will then DELETE the project again by using the Project Object
*/
public class InsertDeleteProjectInDatabaseTest {

	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";

	InsertDatabase dbc;
	DeleteDatabase ddbc;
	Project p = null;
	ArrayList<Suite> returnedSuites;
	ArrayList<InisTest> returnedTests;

	@BeforeClass
	public void setup() {
		System.out.println("\n\nEntered the test setup...");
		
		dbc = new InsertDatabase();
		ddbc = new DeleteDatabase();

	}

	@AfterClass
	public void testCleanup() {
		System.out.println("\n\nEntered the test clean up...");
		
		dbc.disconnect();
		ddbc.disconnect();
		System.out.println("======================Finished test " + this.getClass().getSimpleName() + "======================");
	}

	@Test
	public void testConnectToDatabase() {
		System.out.println("\n\nEntered the testConnectToDatabase...");
		
		dbc.connect(IP, DATABASE, USERNAME, PASSWORD);
	}

	@Test(dependsOnMethods = { "testConnectToDatabase" })
	public void testCreateProjectObject() {
		System.out.println("\n\nEntered the testCreateProjectObject...");
		
		p = new Project("From Insert Test", "15/05/2016");
	}

	@Test(dependsOnMethods = { "testCreateProjectObject" })
	public void testProjectInsert() {
		System.out.println("\n\nEntered the testProjectInsert...");
		
		p.setId(dbc.insertProject(p));
	}

	@Test(dependsOnMethods = { "testProjectInsert" })
	public void testProjectDelete() {
		System.out.println("\n\nEntered the testProjectDelete...");
		
		ddbc.connect(IP, DATABASE, USERNAME, PASSWORD);
		ddbc.deleteProject(p);
	}
}
