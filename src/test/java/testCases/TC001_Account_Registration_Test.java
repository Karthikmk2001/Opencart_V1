package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_Account_Registration_Test extends BaseClass {

    @Test(groups={"Regression","Master"})
    public void verifyAccountRegistration()
    {
        try
        {
            logger.info("***** Starting TC001_Account_Registration_Test *****");

            HomePage homePage=new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked on My_Account Link.....");
            homePage.clickRegister();
            logger.info("Clicked on Register Link.....");

            AccountRegistrationPage accountRegistrationPage=new AccountRegistrationPage(driver);
            accountRegistrationPage.setTxtFirstName(randomStringGenerator().toUpperCase());
            accountRegistrationPage.setTxtLastName(randomStringGenerator().toUpperCase());
            accountRegistrationPage.setTxtEmail(randomStringGenerator()+"@gmail.com");
            accountRegistrationPage.setTxtTelephone(randomNumberGenerator());

            String password=randomAlphaNumericGenerator();
            accountRegistrationPage.setTxtPassword(password);
            accountRegistrationPage.setTxtConfirmPassword(password);
            logger.info("Providing Customer Details.....");

            accountRegistrationPage.acceptPrivacyPolicy();
            logger.info("Selected the Agree Checkbox.....");

            accountRegistrationPage.clickBtnContinue();
            logger.info("Clicked on Continue Button.....");

            String confirmationMessage=accountRegistrationPage.getConfirmationMessage();
            if(confirmationMessage.equals("Your Account Has Been Created!"))
            {
                logger.info("Validated the Expected Message.....");
                Assert.assertTrue(true);
            }
            else
            {
                logger.error("Test Failed.....");
                logger.debug("*** Debug Logs ***");
                Assert.fail();
            }
        }
        catch (Exception exp)
        {
            Assert.fail();
        }
        logger.info("***** Finished TC001_Account_Registration_Test *****");

    }
}
