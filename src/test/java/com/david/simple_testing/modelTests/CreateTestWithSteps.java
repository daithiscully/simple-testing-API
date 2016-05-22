// TODO: Refactor This
	
/*package com.david.simple_testing.modelTests;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.david.simple_testing.models.InisTest;
import com.david.simple_testing.models.Step;
import com.david.simple_testing.models.Suite;

public class CreateTestWithSteps {

	private InisTest t1;
	private ArrayList<Step> testStepsData;
	private final int testId = 1;
	private final String testName = "Hello";
	private final String testDescription = "This test was created in the unit test CreateTestWithSteps";

	@BeforeClass
	public void testSetup() {
		System.out.println("Setting up in the @BeforeClass");
		testStepsData = getSomeSteps();
	}

	@AfterClass
	public void testCleanup() {
		System.out.println("Cleaning down in the @AfterClass");
	}

	@Test
	public void createTest() {
		t1 = new InisTest(testId, new Suite(), testName, testDescription);
	}

	@Test(dependsOnMethods = "createTest")
	public void checkTestName() {
		System.out.println("Validatating Test Name Saved in checkTestName()...");
		Assert.assertTrue(t1.getName().equals(testName), t1.getName() + " is not the same as: " + testName);
	}

	@Test(dependsOnMethods = "createTest")
	public void checkTestDescription() {
		System.out.println("Validatating Test Description Saved in checkTestDescription()...");
		Assert.assertTrue(t1.getDescription().equals(testDescription),
				t1.getDescription() + " is not the same as: " + testDescription);
	}

	@Test(dependsOnMethods = "createTest")
	public void checkTestSteps() {
		System.out.println("Validatating Test Steps Saved in checkTestSteps()...");
		Assert.assertEquals(t1.getSteps(), testStepsData);
	}

	public ArrayList<Step> getSomeSteps() {
		Step s1 = new Step(1, t1, "Wait 5 seconds", "", "");
		Step s2 = new Step(2, t1, "Go to URL", "http://www.google.ie", "");
		Step s3 = new Step(3, t1, "Click Button", "/some/sort/of/xpath", "");
		Step s4 = new Step(4, t1, "Enter Text", "/some/sort/of/xpath", "This is text to be entered...");

		ArrayList<Step> steps = new ArrayList<Step>();
		steps.add(s1);
		steps.add(s2);
		steps.add(s3);
		steps.add(s4);

		return steps;
	}

}
*/