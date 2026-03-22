package Base;

import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.configReader;


public class Base {
protected WebDriver driver;
protected Logger logger;

@BeforeClass
@Parameters({"browser", "url", "execEnv"})
    public void setup(String browser, String baseUrl, String execEnv) throws Exception {
    logger = LogManager.getLogger(this.getClass());


    System.out.println("Browser :" + browser);
    System.out.println("URL :" + baseUrl);
    System.out.println("ENV :" + execEnv);


    driver = DriverFactory.createDriver(browser, execEnv);
    driver.get(configReader.get(baseUrl));

}

@AfterClass
    public void tearDown(){
    driver.quit();
}

}
