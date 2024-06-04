package com.swag.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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

/*		try {
			prop = new Properties(); // here we setup and load the config.properties
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\Configuration\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} */
		try {	
		prop = new Properties();
        String configFilePath = System.getProperty("user.dir") + "\\Configuration\\config.properties";
        File configFile = new File(configFilePath);
        if (configFile.exists()) {
            FileInputStream ip = new FileInputStream(configFilePath);
            prop.load(ip);
            System.out.println("Configuration loaded successfully from: " + configFilePath);
        } else {
            System.out.println("Configuration file not found at: " + configFilePath);
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}  

	public void launchApp() {
		
		 Assert.assertNotNull(prop, "Properties object is null. Ensure loadConfig() is called.");

		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browserName.equalsIgnoreCase("FireFox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("Edge")) {
			WebDriverManager.iedriver().setup();
			driver = new EdgeDriver();

		}

		// URL details
		driver.get(prop.getProperty("url"));
		 //Assert.assertNotNull(prop.getProperty("url"), "URL is not set in properties file.");
		 
		driver.manage().window().maximize();

	}
	
	

}