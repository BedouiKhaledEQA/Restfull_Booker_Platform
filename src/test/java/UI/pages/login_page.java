package UI.pages;

import UI.locators.login_locators;

import org.openqa.selenium.WebDriver;
import utils.configReader;
import utils.waitUtils;


public class login_page extends login_locators {

    WebDriver driver;
    waitUtils wait;

    public login_page(WebDriver driver){
        super(driver); // ✅ initialise les éléments
        this.driver = driver;
        this.wait = new waitUtils(driver);
    }

    public void performLogin(){
        Admin.click();
        Username.sendKeys(configReader.get("Username"));
        Password.sendKeys(configReader.get("Password"));
        wait.waitForClick(LoginButton).click();
    }
}

