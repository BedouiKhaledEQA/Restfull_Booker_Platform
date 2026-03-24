package Test_Cases;

import Base.Base;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class MyListner implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("This is on start");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("This is on success");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("This is on failure");

        Object testClass = result.getInstance();
        WebDriver driver = ((Base) testClass).driver;

        try {
            String path = ((Base) testClass).captureScreen(result.getName());
            System.out.println("Screenshot saved at: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("This is on skipped");
    }

    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }
    @Override public void onStart(org.testng.ITestContext context) { }
    @Override public void onFinish(org.testng.ITestContext context) { }
}