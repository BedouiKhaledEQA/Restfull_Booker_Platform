package Base;

import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class Base {

        public WebDriver driver;
        public Logger logger;
        public static Properties p =new Properties();

        @Parameters({"browser","url","os"})
        @BeforeClass(groups = {"UI","API"})
        protected void setup(String br, String url,String os) throws InterruptedException, IOException {
            FileReader file = new FileReader("src/test/resources/config/config.properties");
            p.load(file);

            logger = LogManager.getLogger(this.getClass());


            if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

                String hubUrl = "http://localhost:4444/wd/hub";
                DesiredCapabilities des = new DesiredCapabilities();
                des.setPlatform(Platform.WIN10);

                switch (br.toLowerCase()) {
                    case "chrome":
                        des.setBrowserName("chrome");
                        break;
                    case "firefox":
                        des.setBrowserName("firefox");
                        break;
                    case "edge":
                        des.setBrowserName("edge");
                        break;
                    default:
                        System.out.println("Invalid Browser");
                        return;
                }

                driver = new RemoteWebDriver(new URL(hubUrl), des);


            }

            else {

                switch (br.toLowerCase()) {
                    case "chrome":
                        driver = new ChromeDriver();
                        break;
                    case "firefox":
                        driver = new FirefoxDriver();
                        break;
                    case "edge":
                        driver = new EdgeDriver();
                        break;
                    default:
                        System.out.println("Invalid Browser");
                        return;
                }
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(url);
            Thread.sleep(10000);
        }
        @AfterClass(groups = {"UI","API"})
        public void TearDown(){
            driver.quit();
        }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        File screenshotsDir = new File(System.getProperty("user.dir") + File.separator + "screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        String fileName = tname + "_" + timeStamp + ".png";
        File targetFile = new File(screenshotsDir, fileName);
        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // chemin relatif pour Extent
        return "screenshots/" + fileName;
    }


    }