package com.david.controllers;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.david.simple_testing.database.ReadDatabase;
import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Project;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

@RestController
public class HomeController {

	private ReadDatabase dbc;
	private Project project;
	private Suite suite;
	private InisTest inisTest;
	private Step step;
	private final String IP = "localhost";
	private final String DATABASE = "simpletesting";
	private final String USERNAME = "testuser";
	private final String PASSWORD = "password";

	@RequestMapping("/project")
	public ResponseEntity<String> project(@RequestParam(value = "id", defaultValue = "1") int id) {
		dbc = new ReadDatabase();
		dbc.connect(IP, DATABASE, USERNAME, PASSWORD);
		project = dbc.readProjectById(id);
		dbc.disconnect();
		System.out.println("=========JSON:==========");
		if (project == null)
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<String>(project.allToJSON(), HttpStatus.OK);
	}

	// TODO: I dont have the other mappings really done right
	// Hard coded values etc. The /project route is returning everything any
	// way...

	@RequestMapping("/suite")
	public ResponseEntity<String> suite(@RequestParam(value = "proj_id", defaultValue = "1") int projId,
			@RequestParam(value = "id", defaultValue = "1") int id) {
		dbc = new ReadDatabase();
		dbc.connect(IP, DATABASE, USERNAME, PASSWORD);
		project = dbc.readProjectById(projId);
		suite = dbc.readSuiteById(id);
		dbc.disconnect();
		if (suite == null)
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<String>(suite.allToJSON(), HttpStatus.OK);
	}

	@RequestMapping("/inisTest")
	public ResponseEntity<String> inisTest(@RequestParam(value = "id", defaultValue = "1") int id) {
		dbc = new ReadDatabase();
		dbc.connect(IP, DATABASE, USERNAME, PASSWORD);
		try {
			project = dbc.readProjectById(id);
			suite = dbc.readAllSuitesByProject(project).get(0);
			inisTest = suite.getInisTests().get(0);
		} catch (SQLException e) {
			return new ResponseEntity<String>(step.allToJSON(), HttpStatus.BAD_REQUEST);
		}

		dbc.disconnect();
		if (inisTest == null)
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<String>(inisTest.allToJSON(), HttpStatus.OK);
	}

	@RequestMapping("/step")
	public ResponseEntity<String> step(@RequestParam(value = "id", defaultValue = "1") int id) {
		dbc = new ReadDatabase();
		dbc.connect(IP, DATABASE, USERNAME, PASSWORD);
		try {
			project = dbc.readProjectById(id);
			suite = dbc.readAllSuitesByProject(project).get(0);
			inisTest = suite.getInisTests().get(0);
			step = inisTest.getSteps().get(0);
		} catch (SQLException e) {
			return new ResponseEntity<String>(step.allToJSON(), HttpStatus.BAD_REQUEST);
		}

		dbc.disconnect();
		if (step != null)
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<String>(step.allToJSON(), HttpStatus.OK);
	}
}
