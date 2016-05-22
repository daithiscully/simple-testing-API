package com.david.simple_testing.databaseTests;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.database.ReadDatabase;
import com.david.simple_testing.database.UpdateDatabase;
import com.david.simple_testing.models.Browser;
import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Suite;

public class TestUpdateInisTestDatabase {

	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";
	
	private final int PROJECT_ID = 1;
	private final String INIS_TEST_NEW_NAME = "I am a bleeding test";
	private final String INIS_TEST_NEW_DESCRIPTION = "Listen up to a story..";
	private final Browser INIS_TEST_NEW_BROWSER = new Browser(1);

	InisTest inisTest;
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
			suite = rdb.readAllSuitesByProject(project).get(0);
			inisTest = suite.getInisTests().get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateInisTest() {
		inisTest.setBrowser(INIS_TEST_NEW_BROWSER);
		inisTest.setName(INIS_TEST_NEW_NAME);
		inisTest.setDescription(INIS_TEST_NEW_DESCRIPTION);;
		inisTest.setSuite(suite);

		udb.updateInisTest(inisTest);
	}
	
	@Test(dependsOnMethods = { "updateInisTest" })
	public void isInisTestUpdated() throws SQLException {
		inisTest = rdb.readAllTestsBySuite(suite).get(0);

		Assert.assertTrue(inisTest.getName().equals(INIS_TEST_NEW_NAME), "ERROR: InisTest name " + inisTest.getName() + " in DB is != " + INIS_TEST_NEW_NAME);
		Assert.assertTrue(inisTest.getDescription().equals(INIS_TEST_NEW_DESCRIPTION), "ERROR: InisTest description " + inisTest.getDescription() + " in DB is != " + INIS_TEST_NEW_DESCRIPTION);
	}

	@AfterClass
	public void teardown() {
		rdb.disconnect();
		udb.disconnect();
		System.out.println("======================Finished test " + this.getClass().getSimpleName() + "======================");
	}

}
