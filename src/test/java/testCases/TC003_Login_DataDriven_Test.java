package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_Login_DataDriven_Test extends BaseClass {

    @Test(dataProvider ="LoginData",dataProviderClass = DataProviders.class)
    public void verifyLoginDDT(String email,String password,String expectedResult)
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
            loginPage.setTxtEmail(email);
            loginPage.setTxtPassword(password);
            logger.info("Provided Login Details.....");

            loginPage.clickLogin();
            logger.info("Clicked on Login Button.....");

            MyAccountPage myAccountPage=new MyAccountPage(driver);
            boolean targetPage=myAccountPage.isMyAccountPageExists();

            if(expectedResult.equalsIgnoreCase("valid"))
            {
                if(targetPage)
                {
                    myAccountPage.clickBtnLogout();
                    myAccountPage.clickBtnContinue();
                    Assert.assertTrue(true);
                }
                else
                {
                    Assert.fail();
                }
            }
            if(expectedResult.equalsIgnoreCase("invalid"))
            {
                if(targetPage)
                {
                    myAccountPage.clickBtnLogout();
                    myAccountPage.clickBtnContinue();
                    Assert.fail();
                }
                else
                {
                    Assert.assertTrue(true);
                }
            }
        }
        catch (Exception exp)
        {
            Assert.fail();
        }
        logger.info("***** Finished TC003_Login_DataDriven_Test *****");
    }
}
