package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_Login_Test extends BaseClass {

    @Test(groups = {"Sanity","Master"})
    public void verifyLogin()
    {
        try
        {
            logger.info("***** Starting TC002_Login_Test *****");
            HomePage homePage=new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked on MyAccount.....");
            homePage.clickLogin();
            logger.info("Clicked on Login.....");

            LoginPage loginPage=new LoginPage(driver);
            loginPage.setTxtEmail(properties.getProperty("email"));
            loginPage.setTxtPassword(properties.getProperty("password"));
            logger.info("Provided Login Details.....");

            loginPage.clickLogin();
            logger.info("Clicked on Login Button.....");

            MyAccountPage myAccountPage=new MyAccountPage(driver);
            boolean targetPage=myAccountPage.isMyAccountPageExists();
            Assert.assertTrue(targetPage);
        }
        catch (Exception exp)
        {
            logger.info("Login Test Failed.....");
            Assert.fail();
        }
        logger.info("***** Finished TC002_Login_Test *****");
    }
}
