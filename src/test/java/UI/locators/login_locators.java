package UI.locators;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class login_locators{
    WebDriver driver;
    public login_locators(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[normalize-space()='Admin']")
    protected WebElement  Admin;
    @FindBy(id = "username")
    protected WebElement Username;
    @FindBy(id = "password")
    protected WebElement Password;
    @FindBy(id = "doLogin")
    protected WebElement LoginButton;
}
