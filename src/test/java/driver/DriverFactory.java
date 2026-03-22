package driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver(String browser , String execEnv) throws Exception {
      WebDriver driver;

      if(execEnv.equalsIgnoreCase("remote")){
          String hubUrl= "http://localhost:4444/wd/hub";
          DesiredCapabilities des =new DesiredCapabilities();
          des.setPlatform(Platform.WIN10);
          switch (browser.toLowerCase()){
              case "chrome" : des.setBrowserName("chrome");
              break;
              case "firefox": des.setBrowserName("firefox");
              break;
              case "edge": des.setBrowserName("edge");
              break;
              default:
                  throw new Exception("Invalid browser");}
          driver=new RemoteWebDriver(new URL(hubUrl),des);
      }else {
          switch (browser.toLowerCase()){
              case "chrome":driver=new ChromeDriver();
              break;
              case "firefox": driver=new FirefoxDriver();
                  break;
              case "edge": driver=new EdgeDriver();
                  break;
              default:throw new RuntimeException("Invalid browser");

          }
          driver.manage().window().maximize();
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      }
      return driver;

    }
}
