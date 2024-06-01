package com.test.Swag.testcases;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.swag.utility.Log;
import com.swag.utility.TestNGListener;
import com.swag.utility.TestRailManager;
import com.aventstack.extentreports.ExtentTest;
import com.swag.base.BaseClass;
import com.swag.pageobjects.HomePage;
import com.swag.pageobjects.LoginPage;
import com.swag.utility.ExcelReader;

import io.github.bonigarcia.wdm.WebDriverManager;


public class scenario_001 extends BaseClass{
	
	// Create objects for all the pages which required in scenario
	LoginPage loginpage;
	HomePage homepage;

	
	@BeforeMethod
	public void setup() {
		String testRunId = prop.getProperty("testrail.run_id");
		TestRailManager.TEST_RUN_ID = testRunId;
		
		launchApp();
	}
	
	@AfterMethod
	public void tearDown() {
		
		// Close the browser
        if (driver != null) {
            driver.quit();
        }
	}
	
	@Test(priority=1)
    public void startpageAndlogin1() throws Throwable  {
		
	  //  ExtentTest test = TestNGListener.extentTest.get();
      //  test = TestNGListener.extentTest.get();
		
		//TestRail
		testCaseId = 9560;
		
		// Logging the messsage
		Log.startTestCase("scenario_01");
		Log.info("This is scenario_01");
		
		
		 // Fetch data from ExcelReader
        Map<String, String> testData = ExcelReader.getData("Scenario_01");
		
		loginpage = new LoginPage();
		
		// Use data in your test
		homepage = loginpage.login(testData.get("Username"), testData.get("Password"));
		
		//test.info("Starting testMethod1");
		
		String title = homepage.title();
		Assert.assertEquals(title,"Swag Labs");
		
	    Log.endTestCase("scenario_01");

		
	}
	

	@Test(priority=2)
    public void startpageAndlogin2() throws Throwable {	
		
		//TestRail
		testCaseId = 9561;
		
		Log.startTestCase("scenario_02");
		Log.info("This is scenario_02");
		
		
		 // Fetch data from ExcelReader
        Map<String, String> testData = ExcelReader.getData("Scenario_02");

        loginpage = new LoginPage();
        
        // Use data in your test
        loginpage.login(testData.get("Username"), testData.get("Password"));
        
        String errormsg_1 = loginpage.error1.getText();
		Assert.assertEquals(errormsg_1,"Epic sadface: Sorry, this user has been locked out");
		
		Log.endTestCase("scenario_02");

	}
	
	

}
