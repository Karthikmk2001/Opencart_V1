package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")//My Account Heading
    WebElement txtHeading;

    @FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
    WebElement btnLogout;

    @FindBy(xpath = "//a[normalize-space()='Continue']")
    WebElement btnContinue;

    public boolean isMyAccountPageExists()
    {
        try
        {
            return (txtHeading.isDisplayed());
        }
        catch(Exception exp)
        {
            return false;
        }
    }

    public void clickBtnLogout()
    {
        btnLogout.click();
    }

    public void clickBtnContinue()
    {
        btnContinue.click();
    }
}
