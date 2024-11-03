package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String rep_name;

    public void onStart(ITestContext testContext)
    {
        /*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt=new Date();
        String current_date_timestamp=df.format(dt);*/

        String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        rep_name="Test-Report"+timestamp+".html";
        sparkReporter=new ExtentSparkReporter(".\\reports\\"+rep_name);

        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
        sparkReporter.config().setReportName("Opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent=new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application","Opencart");
        extent.setSystemInfo("Module","Admin");
        extent.setSystemInfo("Sub Module","Customers");
        extent.setSystemInfo("User Name",System.getProperty("user.name"));
        extent.setSystemInfo("Environment","QA");

        String os=testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System",os);

        String browser=testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser",browser);

        List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty())
        {
            extent.setSystemInfo("Groups",includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result)
    {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS,result.getName()+" got successfully executed");
    }

    public void onTestFailure(ITestResult result)
    {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL,result.getName()+" got failed");
        test.log(Status.INFO,result.getThrowable().getMessage());

        try
        {
            String img_path=new BaseClass().captureScreen(result.getName());//remember here since we are creating a new object of base class there is a chance of creating multiple instance of webDriver , so to avoid it we make webDriver as static in base class.
            test.addScreenCaptureFromPath(img_path);
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result)
    {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP,result.getName()+" got skipped");
        test.log(Status.INFO,result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext)
    {
        extent.flush();

        String path_of_extent_report=System.getProperty("user.dir")+"\\reports\\"+rep_name;
        File extent_report=new File(path_of_extent_report);

        //Automatically open Report in Browser
        try
        {
            Desktop.getDesktop().browse(extent_report.toURI());
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }

        //Sending Direct Email of the Report Generated
        /*try
        {
            URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+rep_name);

            ImageHtmlEmail email=new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("");//set your hostname(gmail,yahoo etc)
            email.setSmtpPort(456);
            email.setAuthenticator(new DefaultAuthenticator("email","password"));
            email.setSSLOnConnect(true);
            email.setFrom("sender email");
            email.setSubject("Test Results");
            email.setMsg("Please find the attached report");
            email.addTo("receiver email");
            email.attach(url,"extent report","Please check the report");
            email.send();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }*/
    }
}
