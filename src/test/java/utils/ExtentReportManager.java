package utils;

import Base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private String repName;

    @Override
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + repName);
        sparkReporter.config().setDocumentTitle("Automation Test");
        sparkReporter.config().setReportName("Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "OrangeHRM");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester Name", "Khaled");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Module", "Admin");

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        if (browser != null) {
            extent.setSystemInfo("Browser", browser);
        }

        List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includeGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includeGroups.toString());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.INFO, "Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test passed: " + result.getName());
        captureAndAttach(result, Status.PASS);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test failed: " + result.getName());
        test.log(Status.INFO, "Cause: " + result.getThrowable());
        captureAndAttach(result, Status.FAIL);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test skipped: " + result.getName());
        captureAndAttach(result, Status.SKIP);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        try {
            File extentReport = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator + repName);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(extentReport.toURI());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void captureAndAttach(ITestResult result, Status status) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof Base) { // uniquement si le test hérite de Base
            try {
                Base testClass = (Base) testInstance;
                if (testClass.driver != null) {
                    String imgPath = captureScreenForReport(testClass, result.getName());
                    test.addScreenCaptureFromPath(imgPath, "Screenshot [" + status + "]");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Test non-UI, pas de screenshot
            test.log(Status.INFO, "No screenshot for non-UI test: " + result.getName());
        }
    }

    /** Capture l’écran et retourne le chemin relatif pour ExtentReports */
    private String captureScreenForReport(Base base, String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotsDirPath = System.getProperty("user.dir") + File.separator + "screenshots";
        File screenshotsDir = new File(screenshotsDirPath);
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        String fileName = tname + "_" + timeStamp + ".png";
        File targetFile = new File(screenshotsDir, fileName);

        File sourceFile = ((org.openqa.selenium.TakesScreenshot) base.driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return "../screenshots/" + fileName; // chemin relatif pour le rapport
    }
}