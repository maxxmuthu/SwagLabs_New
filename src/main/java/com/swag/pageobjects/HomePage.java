package com.swag.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.swag.base.BaseClass;
import com.swag.utility.Utils;

public class HomePage extends BaseClass {

	Utils util = new Utils();

	@FindBy(xpath = "//div[text()='Swag Labs']")
	public WebElement homePageTitle;

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public String title() throws Throwable {

			String homepagetext = util.get_Text(homePageTitle, driver);
			Thread.sleep(2000);
			System.out.println(homepagetext);
			Thread.sleep(2000);
			return homepagetext;
			
		}
		
	
		    
	
}
