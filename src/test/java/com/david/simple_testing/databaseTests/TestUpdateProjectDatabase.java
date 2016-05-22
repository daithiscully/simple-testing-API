package com.david.simple_testing.databaseTests;

import java.sql.SQLException;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.database.ReadDatabase;
import com.david.simple_testing.database.UpdateDatabase;
import com.david.simple_testing.models.Project;

public class TestUpdateProjectDatabase {

	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";

	private final int PROJECT_ID = 1;
	private final String PROJECT_NEW_NAME = "Daft Punk";
	private final String PROJECT_NEW_CREATED_ON = "11/1/1234";

	Project project;
	ReadDatabase rdb;
	UpdateDatabase udb;

	@BeforeClass
	public void setup() {
		rdb = new ReadDatabase();
		udb = new UpdateDatabase();
		rdb.connect(IP, DATABASE, USERNAME, PASSWORD);
		udb.connect(IP, DATABASE, USERNAME, PASSWORD);
		project = rdb.readProjectById(PROJECT_ID);
	}

	@Test
	public void updateProject() {
		project.setCreatedOn(PROJECT_NEW_CREATED_ON);
		project.setName(PROJECT_NEW_NAME);

		udb.updateProject(project);
	}

	@Test(dependsOnMethods = { "updateProject" })
	public void isProjectUpdated() throws SQLException {
		project = rdb.readProjectById(PROJECT_ID);

		AssertJUnit.assertTrue(project.getName().equals(PROJECT_NEW_NAME));
		AssertJUnit.assertTrue(project.getCreatedOn().equals(PROJECT_NEW_CREATED_ON));
	}

	@AfterClass
	public void teardown() {
		rdb.disconnect();
		udb.disconnect();
		System.out.println(
				"======================Finished test " + this.getClass().getSimpleName() + "======================");
	}

}
