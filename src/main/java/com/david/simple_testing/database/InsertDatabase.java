package com.david.simple_testing.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

public class InsertDatabase extends DatabaseConnection {

	// Create a Project in DB
	public int insertProject(Project p) {

		int lastInsertId = 0;

		String sql = " insert into Projects (name, created_on)" + " values (?, ?)";

		System.out.println("Creating insertProject statement...");
		System.out.println(sql);

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, p.getName());
			preparedStmt.setString(2, p.getCreatedOn());
			// execute the preparedstatement
			preparedStmt.execute();

			// Get the ID MySQL has assigned to this object in the database
			ResultSet keys = preparedStmt.getGeneratedKeys();
			keys.next();
			lastInsertId = keys.getInt(1);

			return lastInsertId;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Insert Project");
			e.printStackTrace();
			return lastInsertId;
		}

	}

	// Create a Suite in DB
	public int insertSuite(Suite s) {

		int lastInsertId = 0;

		String sql = " insert into Suites (project, name, description)" + " values (?, ?, ?)";

		System.out.println("Creating insertSuite statement...");
		System.out.println(sql);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, s.getProject().getId());
			preparedStmt.setString(2, s.getName());
			preparedStmt.setString(3, s.getDescription());
			preparedStmt.execute();

			ResultSet keys = preparedStmt.getGeneratedKeys();
			keys.next();
			lastInsertId = keys.getInt(1);

			return lastInsertId;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Insert Suite");
			e.printStackTrace();
			return lastInsertId;
		}

	}
	// Create an InisTest in DB
	public int insertInisTest(InisTest it) {

		int lastInsertId = 0;

		String sql = " insert into InisTests (suite, name, browser, description)" + " values (?, ?, ?, ?)";

		System.out.println("Creating insertInisTest statement...");
		System.out.println(sql);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, it.getSuite().getId());
			preparedStmt.setString(2, it.getName());
			preparedStmt.setInt(3, it.getBrowser().getId());
			preparedStmt.setString(4, it.getDescription());
			preparedStmt.execute();

			ResultSet keys = preparedStmt.getGeneratedKeys();
			keys.next();
			lastInsertId = keys.getInt(1);

			return lastInsertId;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Insert InisTest");
			e.printStackTrace();
			return lastInsertId;
		}

	}
	
	// TODO: Create a Step in DB
	public int insertStep(Step step) {

		int lastInsertId = 0;

		String sql = " insert into Steps (test, action, action_data_1, action_data_2)" + " values (?, ?, ?, ?)";

		System.out.println("Creating insertStep statement...");
		System.out.println(sql);

		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, step.getInisTest().getId());
			preparedStmt.setString(2, step.getAction());
			preparedStmt.setString(3, step.getActionData1());
			preparedStmt.setString(4, step.getActionData2());
			preparedStmt.execute();

			ResultSet keys = preparedStmt.getGeneratedKeys();
			keys.next();
			lastInsertId = keys.getInt(1);

			return lastInsertId;
		} catch (SQLException e) {
			System.out.println("Got an SQLException in the Insert Step");
			e.printStackTrace();
			return lastInsertId;
		}

	}
	
}
