package com.swag.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.swag.base.BaseClass;



public class TestNGListener extends BaseClass implements ITestListener{
	
	
	private static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    //private WebDriver driver; // Assuming you have a WebDriver instance

    @Override
    public void onStart(ITestContext context) {
        extentReports = ExtentManager.createInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        extentTest.get().assignAuthor("maxxmuthu");
        extentTest.get().assignCategory("Smoke Tests");
        extentTest.get().getModel().setDescription("This test verifies the login functionality.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
        extentTest.get().log(Status.PASS, MarkupHelper.createLabel("Test passed", ExtentColor.GREEN))
                       .addScreenCaptureFromPath(screenshotPath);
        
     // Add results to TestRail
        Object testClass = result.getInstance();
        try {
            Field field = testClass.getClass().getField("testCaseId");
            int testCaseId = field.getInt(testClass);
            TestRailManager.addResultsForTestCase(testCaseId, TestRailManager.TEST_CASE_PASS_STATUS, "Test Case is passed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
        extentTest.get().log(Status.FAIL, MarkupHelper.createLabel("Test failed", ExtentColor.RED))
                   .log(Status.FAIL, result.getThrowable())
                   .addScreenCaptureFromPath(screenshotPath);
        
     // Add results to TestRail with custom log message
        Object testClass = result.getInstance();
        try {
            Field field = testClass.getClass().getField("testCaseId");
            int testCaseId = field.getInt(testClass);
            String logMessage = "Test Case got Failed." + " Method: " + result.getMethod().getMethodName() + " : FAILED. " + result.getThrowable().getMessage();
            TestRailManager.addResultsForTestCase(testCaseId, TestRailManager.TEST_CASE_FAIL_STATUS, logMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String summary = "Test failed: " + result.getMethod().getMethodName();
        JiraIssueCreator.createJiraIssue(summary);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }
    

    public String captureScreenshot(String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String fileName = screenshotName + "_" + System.currentTimeMillis() + ".png"; // Append timestamp
        String destination = System.getProperty("user.dir") + "/test-output/screenshots/" + fileName;
        try {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/test-output/screenshots/")); // Ensure directory exists
            Files.move(source.toPath(), Paths.get(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

}
    


    
