package com.swag.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentTest;
import com.swag.utility.TestNGListener;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static Properties prop;
	public static WebDriver driver;
	public int testCaseId;

	// To Create a config.properties file -> 1. Right click on the folder --> New --> File (AND) 2. Enter file name as config.properties and click finish

	// loadConfig method is to load the configuration
	@BeforeSuite()
	public void loadConfig() {

		try {
			prop = new Properties(); // here we setup and load the config.properties
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\Configuration\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	public void setDriver(String browser) throws MalformedURLException {  // for non-parallel test - remove 'String browser' parameter

		if (Boolean.getBoolean("selenium.grid.enabled")) {
			this.driver = getRemoteDriver(browser);                       // for non-parallel test - remove 'browser' parameter
		} else {
			this.driver = getLocalDriver();
		}
		
		// URL details
			driver.get(prop.getProperty("url"));			 
		    driver.manage().window().maximize();

	}

	public WebDriver getRemoteDriver(String browser) throws MalformedURLException {    // for non-parallel test - remove 'String browser' parameter
		Capabilities capabilities = null;
		//String browser = System.getProperty("browser");              // for non-parallel test - it is required		
		//String browser = prop.getProperty("browser");

		if (browser != null && browser.equalsIgnoreCase("chrome")) {
			capabilities = new ChromeOptions();

		} else if (browser != null && browser.equalsIgnoreCase("edge")) {
			capabilities = new EdgeOptions();

		} else {
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
	}

	public WebDriver getLocalDriver() {
		String browser = prop.getProperty("browser");

		if (browser != null && browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver();

		} else if (browser != null && browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			return new EdgeDriver();

		} else {
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
	}

	

}