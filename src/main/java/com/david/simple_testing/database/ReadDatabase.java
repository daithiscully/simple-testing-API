package com.david.simple_testing.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.david.simple_testing.models.Browser;
import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

public class ReadDatabase extends DatabaseConnection {

	public Browser readBrowserById(int browserId) throws SQLException {
		String sql = String.format("SELECT * FROM Browsers WHERE id=%d", browserId);
		Browser b = null;

		System.out.println("Creating readBrowserById statement...");
		System.out.println(sql);

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			// Retrieve by column name
			int id = rs.getInt("id");
			String browserName = rs.getString("name");
			String browserVersion = rs.getString("version");
			b = new Browser(id, browserName, browserVersion);
		}
		rs.close();

		return b;
	}
	
	public Project readProjectById(int projectId) {
		String sql = String.format("SELECT * FROM Projects WHERE id=%d", projectId);
		Project p = null;

		System.out.println("Creating readProjectById statement...");
		System.out.println(sql);

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String projectName = rs.getString("name");
				String projectCreatedOn = rs.getString("created_on");
				p = new Project(id, projectName, projectCreatedOn);
				p.setSuites(readAllSuitesByProject(p));
			}
			rs.close();
			return p;
		} catch (SQLException e) {
			System.out.println("ERROR: in the the readProjectByID() in ReadDatabase.java");
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<Suite> readAllSuitesByProject(Project project) throws SQLException {
		String sql = String.format("SELECT * FROM Suites WHERE project=%d", project.getId());
		ArrayList<Suite> results = new ArrayList<>();
		Suite s;

		System.out.println("Creating readAllSuitesByProject statement...");
		System.out.println(sql);

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt("id");
			// int project_id = rs.getInt("project");
			String suiteName = rs.getString("name");
			String suiteDescription = rs.getString("description");
			s = new Suite(id, project, suiteName, suiteDescription);
			s.setInisTests(readAllTestsBySuite(s));
			results.add(s);
		}
		rs.close();

		return results;
	}

	public ArrayList<InisTest> readAllTestsBySuite(Suite suite) throws SQLException {
		String sql = String.format("SELECT * FROM InisTests WHERE suite=%d", suite.getId());
		ArrayList<InisTest> results = new ArrayList<>();
		InisTest t;

		System.out.println("Creating readAllTestsBySuite statement...");
		System.out.println(sql);

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt("id");
			// int project_id = rs.getInt("project");
			String inisTestName = rs.getString("name");
			int browserId = rs.getInt("browser");
			String inisTestDescription = rs.getString("description");
			t = new InisTest(id, suite, inisTestName, readBrowserById(browserId), inisTestDescription);
			t.setSteps(readAllStepsByTest(t));
			results.add(t);
		}
		rs.close();

		return results;
	}

	public ArrayList<Step> readAllStepsByTest(InisTest test) throws SQLException {
		String sql = String.format("SELECT * FROM Steps WHERE test=%d", test.getId());
		ArrayList<Step> results = new ArrayList<>();
		Step s;

		System.out.println("Creating readAllStepsByTest statement...");
		System.out.println(sql);

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt("id");
			// int test_id = rs.getInt("test");
			String stepAction = rs.getString("action");
			String stepActionData1 = rs.getString("action_data_1");
			String stepActionData2 = rs.getString("action_data_2");
			s = new Step(id, test, stepAction, stepActionData1, stepActionData2);
			results.add(s);
		}
		rs.close();

		return results;
	}

	public Project getAllProjectData(int projectId) throws SQLException {
		Project p = readProjectById(projectId);

		// Get all suites belonging to that project
		ArrayList<Suite> projectSuites = readAllSuitesByProject(p);
		for (Suite s : projectSuites) {
			// Get all InisTests belonging to that Suite
			ArrayList<InisTest> suitesTests = readAllTestsBySuite(s);

			for (InisTest t : suitesTests) {

				// Get all Steps belonging to that InisTest
				ArrayList<Step> testsSteps = readAllStepsByTest(t);

				for (Step step : testsSteps) {
					// Add each Step to the InisTest
					t.addStep(step);
				}
				// Add each InisTest to the Suite
				s.addInisTest(t);
			}
			// Add each Suite to the project ADD LAST after every else has been
			// ordered. Backwards
			p.addSuite(s);
		}
		return p;
	}

	public Suite readSuiteById(int suiteId) {
		String sql = String.format("SELECT * FROM Suites WHERE id=%d", suiteId);
		Suite s = null;
		Project p = null;
		ArrayList<InisTest> inisTests = new ArrayList<>();

		System.out.println("Creating readSuiteById statement...");
		System.out.println(sql);

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int project_id = rs.getInt("project");
				String suiteName = rs.getString("name");
				String suiteDescription = rs.getString("description");
				p = readProjectById(project_id);
				
				s = new Suite(id, p, suiteName, suiteDescription);
				inisTests = readAllTestsBySuite(s);
				s.setInisTests(inisTests);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}// end ReadDatabase