package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txtFirstName;

    @FindBy(xpath="//input[@id='input-lastname']")
    WebElement txtLastName;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement txtTelephone;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement txtConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement chkAgree;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    public void setTxtFirstName(String firstName)
    {
        txtFirstName.sendKeys(firstName);
    }

    public void setTxtLastName(String lastName)
    {
        txtLastName.sendKeys(lastName);
    }

    public void setTxtEmail(String email)
    {
        txtEmail.sendKeys(email);
    }

    public void setTxtTelephone(String telephone)
    {
        txtTelephone.sendKeys(telephone);
    }

    public void setTxtPassword(String password)
    {
        txtPassword.sendKeys(password);
    }

    public void setTxtConfirmPassword(String password)
    {
        txtConfirmPassword.sendKeys(password);
    }

    public void acceptPrivacyPolicy()
    {
        chkAgree.click();
    }

    public void clickBtnContinue()
    {
        btnContinue.click();
    }

    public String getConfirmationMessage()
    {
        try{
            return (msgConfirmation.getText());
        }
        catch (Exception exp)
        {
            return (exp.getMessage());
        }
    }
}
