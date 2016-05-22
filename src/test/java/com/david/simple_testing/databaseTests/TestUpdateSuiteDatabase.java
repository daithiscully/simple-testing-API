package com.david.simple_testing.databaseTests;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.database.ReadDatabase;
import com.david.simple_testing.database.UpdateDatabase;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Suite;

public class TestUpdateSuiteDatabase {

	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";
	
	private final int PROJECT_ID = 1;
	private final String SUITE_NEW_NAME = "Human After All";
	private final String SUITE_NEW_DESCRIPTION = "47/4/1234";

	Suite suite;
	Project project;
	ReadDatabase rdb;
	UpdateDatabase udb;

	@BeforeClass
	public void setup() {
		rdb = new ReadDatabase();
		udb = new UpdateDatabase();
		rdb.connect(IP, DATABASE, USERNAME, PASSWORD);
		udb.connect(IP, DATABASE, USERNAME, PASSWORD);
		
		try {
			project = rdb.readProjectById(PROJECT_ID);
			System.out.println("--++ Suite reurned at arraylist 0: == " + rdb.readAllSuitesByProject(project).get(0));
			suite = rdb.readAllSuitesByProject(project).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateSuite() {
		suite.setProject(project);
		suite.setName(SUITE_NEW_NAME);
		suite.setDescription(SUITE_NEW_DESCRIPTION);

		udb.updateSuite(suite);
	}
	
	@Test(dependsOnMethods = { "updateSuite" })
	public void isSuiteUpdated() throws SQLException {
		suite = rdb.readAllSuitesByProject(project).get(0);

		Assert.assertTrue(suite.getName().equals(SUITE_NEW_NAME), "ERROR: Suite name " + suite.getName() + " in DB is != " + SUITE_NEW_NAME);
		Assert.assertTrue(suite.getDescription().equals(SUITE_NEW_DESCRIPTION), "ERROR: Suite description " + suite.getDescription() + " in DB is != " + SUITE_NEW_DESCRIPTION);
		Assert.assertTrue(suite.getProject().getId() == PROJECT_ID, "ERROR: Project Id " + suite.getProject().getId() + " in DB is != " + PROJECT_ID);
	}

	@AfterClass
	public void teardown() {
		rdb.disconnect();
		udb.disconnect();
		System.out.println("======================Finished test " + this.getClass().getSimpleName() + "======================");
	}

}
