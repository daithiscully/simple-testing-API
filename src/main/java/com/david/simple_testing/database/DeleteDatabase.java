package com.david.simple_testing.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

public class DeleteDatabase extends DatabaseConnection {

	// Delete a Project in DB
	public boolean deleteProject(Project p) {

		String sql = String.format(" DELETE FROM `simpletesting`.`Projects` WHERE `id`='%d';", p.getId());

		System.out.println("Creating deleteProject statement...");
		System.out.println(sql);

		// create the MySQL insert prepared statement
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql);
			// execute the prepared statement
			preparedStmt.execute();

			resetProjectAutoIncrement("Projects");
			return true;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Delete Project");
			e.printStackTrace();

			return false;
		}

	}

	// DELETE a Suite in DB
	public boolean deleteSuite(Suite s) {

		String sql = String.format(" DELETE FROM `simpletesting`.`Suites` WHERE `id`='%d';", s.getId());

		System.out.println("Creating deleteSuite statement...");
		System.out.println(sql);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.execute();

			resetProjectAutoIncrement("Suites");
			return true;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Delete Suite");
			e.printStackTrace();

			return false;
		}

	}

	// DELETE an InisTest in DB
	public boolean deleteInisTest(InisTest it) {

		String sql = String.format(" DELETE FROM `simpletesting`.`InisTests` WHERE `id`='%d';", it.getId());

		System.out.println("Creating deleteInisTest statement...");
		System.out.println(sql);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.execute();

			resetProjectAutoIncrement("InisTests");
			return true;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Delete Inis Test");
			e.printStackTrace();

			return false;
		}

	}

	// DELETE a Step in DB
	public boolean deleteStep(Step s) {

		String sql = String.format(" DELETE FROM `simpletesting`.`Steps` WHERE `id`='%d';", s.getId());

		System.out.println("Creating deleteStep statement...");
		System.out.println(sql);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql);
			preparedStmt.execute();

			resetProjectAutoIncrement("Steps");
			return true;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Delete Step");
			e.printStackTrace();

			return false;
		}

	}

	// DELETE Project which will DELETE all foreign key shit in one method
	public boolean deleteAllProjectData(Project project) {

		// Delete from lower tables in the hierarchy

		ArrayList<Suite> allSuites = project.getSuites();
		for (Suite suite : allSuites) {
			ArrayList<InisTest> allInisTests = suite.getInisTests();

			for (InisTest inisTest : allInisTests) {
				ArrayList<Step> allSteps = inisTest.getSteps();

				for (Step step : allSteps) {
					deleteStep(step);
					resetProjectAutoIncrement("Steps");

				}
				deleteInisTest(inisTest);
				resetProjectAutoIncrement("InisTests");
			}
			deleteSuite(suite);
			resetProjectAutoIncrement("Suites");

		}
		if (deleteProject(project)) {
			resetProjectAutoIncrement("Projects");
			return true;
		} else
			return false;
	}

	// Reset Auto_increment for each table
	public void resetProjectAutoIncrement(String table) {
		// Get the highest number in the auto incrementing column
		String getMaxIdValueSQL = String.format("SELECT MAX( id ) FROM simpletesting." + table + " ;");

		int autoIncrementValue = 0;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getMaxIdValueSQL);
			while (rs.next()) {
				autoIncrementValue = rs.getInt("Max( id )");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the getMaxIdValueSQL");
			e.printStackTrace();
		}

		String setAutoIncrementValue = String.format(" ALTER TABLE simpletesting." + table + " AUTO_INCREMENT = %d;",
				autoIncrementValue);
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(setAutoIncrementValue);
			preparedStmt.execute();

		} catch (SQLException e) {
			System.out.println("Got an SQLException in the setAutoIncrementValue");
			e.printStackTrace();
		}
	}
}
