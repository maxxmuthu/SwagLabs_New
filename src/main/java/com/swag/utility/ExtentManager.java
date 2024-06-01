package com.swag.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentManager {
	
	private static ExtentReports extent;
    private static ExtentSparkReporter htmlReporter;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    public static ExtentReports createInstance() {
    	String filePath = System.getProperty("user.dir") + "/test-output/extent-report.html";
        htmlReporter = new ExtentSparkReporter(filePath).viewConfigurer().viewOrder().as(new ViewName[] {ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY, ViewName.AUTHOR}).apply(); //order 
        htmlReporter.config().setDocumentTitle("Automation Test Report");     // Page Title
        htmlReporter.config().setReportName("Regression Test Suite");    // Report Name
        htmlReporter.config().setTheme(Theme.STANDARD); // Set Extent Report Theme
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        // Set system info
        extent.setSystemInfo("OS", "Windows 10");
        extent.setSystemInfo("Java Version", "21");
        extent.setSystemInfo("Selenium", "4.18.1");
        
        

        return extent;
    }

}
