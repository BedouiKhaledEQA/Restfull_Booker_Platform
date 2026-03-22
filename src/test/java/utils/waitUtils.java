package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class waitUtils {

    WebDriver driver;
    WebDriverWait wait;

    public waitUtils(WebDriver driver){
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement waitForVisibility(By locators){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locators));
    }

    public WebElement waitForClick(By locators){
        return wait.until(ExpectedConditions.elementToBeClickable(locators));
    }

    public WebElement waitForPresence(By locators){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locators));
    }
}
