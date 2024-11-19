package testBase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties properties;

    @BeforeClass(groups = {"Sanity","Regression","Master"})
    @Parameters({"os","browser"})
    public void setUp(@Optional("linux") String os,@Optional("chrome") String browser) throws IOException {
        //Loading config.properties
        FileReader file=new FileReader("./src//test//resources//config.properties");
        properties=new Properties();
        properties.load(file);

        logger= LogManager.getLogger(this.getClass());

        //REMOTE
        if(properties.getProperty("execution_env").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities capabilities=new DesiredCapabilities();
            switch (os.toLowerCase())
            {
                case "windows":capabilities.setPlatform(Platform.WINDOWS);
                    break;
                case "mac":capabilities.setPlatform(Platform.MAC);
                    break;
                case "linux":capabilities.setPlatform(Platform.LINUX);
                    break;
                default:
                    System.out.println("No matching Operating System");
                    return;
            }

            switch(browser.toLowerCase())
            {
                case "chrome":capabilities.setBrowserName("chrome");
                    break;
                case "edge":capabilities.setBrowserName("edge");
                    break;
                case "firefox":capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("No matching browser");
                    return;
            }
            driver=new RemoteWebDriver(new URL("http://localhost:4444"),capabilities);
        }

        if(properties.getProperty("execution_env").equalsIgnoreCase("local"))
        {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            WebDriverManager.chromedriver().setup();
            // Set path to ChromeDriver for AWS Linux
            //System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
            switch (browser.toLowerCase())
            {
                case "chrome":driver=new ChromeDriver(options);
                    break;
                case "edge":driver=new EdgeDriver();
                    break;
                case "firefox":driver=new FirefoxDriver();
                    break;
                default:
                    System.out.println("Invalid Browser!!!");
                    return;
            }
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(properties.getProperty("appURL"));//Reading url from properties file
        driver.manage().window().maximize();
    }

    @AfterClass(groups = {"Sanity","Regression","Master"})
    public void tearDown()
    {
        driver.quit();
    }

    public String randomStringGenerator()
    {
        return RandomStringUtils.randomAlphabetic(6);
    }

    public String randomNumberGenerator()
    {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumericGenerator()
    {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public String captureScreen(String tname)
    {
        String timestamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
        File source_file=takesScreenshot.getScreenshotAs(OutputType.FILE);

        String target_file_path=System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timestamp+".png";
        File targetFile=new File(target_file_path);

        source_file.renameTo(targetFile);
        return target_file_path;
    }
}
