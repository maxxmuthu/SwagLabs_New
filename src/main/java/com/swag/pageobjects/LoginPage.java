package com.swag.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.swag.base.BaseClass;
import com.swag.utility.Utils;

public class LoginPage extends BaseClass{
	
	Utils util = new Utils();
	
	@FindBy(id="user-name")
	public WebElement userName;
	
	@FindBy(id="password")
	public WebElement password;

	@FindBy(xpath="//input[@id='login-button']")
	public WebElement signInBtn;
	
	@FindBy(xpath="//h3[text()='Epic sadface: Sorry, this user has been locked out.']")
	public WebElement error1;
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	public HomePage login(String uname, String pswd) throws Throwable {
	
		util.type(userName, uname);  // sendKeys username
		util.type(password, pswd);   // sendKeys password
		util.clickElement(driver, signInBtn);    // click button

		Thread.sleep(2000);
		return new HomePage();     // since next operation is on homepage , we need to return it
	}

}
