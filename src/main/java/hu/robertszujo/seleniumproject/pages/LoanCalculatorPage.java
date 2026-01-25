package hu.robertszujo.seleniumproject.pages;

import com.aventstack.extentreports.ExtentTest;
import hu.robertszujo.seleniumproject.utils.ElementActions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoanCalculatorPage extends BasePageObject {

    public LoanCalculatorPage(WebDriver driver, ExtentTest reporter) {
        super(driver, reporter);
    }

    // *** Elements ***

    @FindBy(css = "div[class='content_hitelmaximum']")
    private WebElement calculatorForm;

    @FindBy(id = "meletkor")
    private WebElement ageInputField;

    @FindBy(id = "eletkor_error")
    private WebElement ageErrorMessage;

    @FindBy(id = "ingatlan_erteke")
    private WebElement propertyValueInputField;

    @FindBy(id = "ingatlan_erteke_error")
    private WebElement propertyValueErrorMessage;

    @FindBy(id = "egyedul")
    private WebElement aloneRadioButton;

    @FindBy(id = "tobben")
    private WebElement severalRadioButton;

    @FindBy(id = "mjovedelem")
    private WebElement salaryInputField;

    @FindBy(id = "mjovedelem_error")
    private WebElement salaryErrorMessage;

    @FindBy(id = "meglevo_torleszto")
    private WebElement repaymentInputField;

    @FindBy(id = "meglevo_torleszto_error")
    private WebElement repaymentErrorMessage;

    @FindBy(id = "kedvezmeny_jovairasm")
    private WebElement discountCheckbox;

    @FindBy(xpath = "//*[@id=\"mennyit_button\"]/div/input")
    private WebElement calculationButton;

    @FindBy(id = "max_eredmeny")
    private WebElement calculationResult;

    @FindBy(id = "box_1_max_desktop")
    private WebElement firstMaximumLoanAmount;

    @FindBy(id = "kedvezmeny_biztositasm")
    private WebElement insuranceDiscountCheckbox;

    @FindBy(id = "box_1_thm")
    private WebElement firstThm;

    // *** Element methods ***

    public void waitForCalculatorFormToBeDisplayed() {
        reporter.info("Waiting for calculator form to be displayed");
        ElementActions.waitForElementToBeDisplayed(calculatorForm, driver);
    }

    public boolean isCalculatorFormDisplayedAfterWaiting() {
        try {
            waitForCalculatorFormToBeDisplayed();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void typeIntoAgeInputField(String age) {
        reporter.info("Type into age input field: " + age);
        scrollToElement(ageInputField);
        ageInputField.sendKeys(age);
        blurInputField(ageInputField);
    }

    public boolean isAgeErrorMessageDisplayed() {
        reporter.info("Checking if age error message is displayed.");
        ElementActions.waitForElementToBeDisplayed(ageErrorMessage, driver);
        return ageErrorMessage.isDisplayed();
    }

    public void typeIntoPropertyValueInputField(String value) {
        reporter.info("Type into property value input field: " + value);
        scrollToElement(propertyValueInputField);
        propertyValueInputField.sendKeys(value);
        blurInputField(propertyValueInputField);
    }

    public boolean isPropertyValueErrorMessageDisplayed() {
        reporter.info("Checking if property value error message is displayed.");
        ElementActions.waitForElementToBeDisplayed(propertyValueErrorMessage, driver);
        return propertyValueErrorMessage.isDisplayed();
    }

    public void clickOnAloneRadioButton() {
        reporter.info("Click on alone radio button");
        aloneRadioButton.click();
    }

    public void clickOnSeveralRadioButton() {
        reporter.info("Click on several radio button");
        severalRadioButton.click();
    }

    public void typeIntoSalaryInputField(String salary) {
        reporter.info("Type into salary input field: " + salary);
        scrollToElement(salaryInputField);
        salaryInputField.sendKeys(salary);
        blurInputField(salaryInputField);
    }

    public String getSalaryErrorMessageIfItIsDisplayed() {
        reporter.info("Checking if salary error message is displayed.");
        ElementActions.waitForElementToBeDisplayed(salaryErrorMessage, driver);
        return salaryErrorMessage.getText();
    }

    public void typeIntoRepaymentInputField(String repayment) {
        reporter.info("Type into repayment input field: " + repayment);
        scrollToElement(repaymentInputField);
        repaymentInputField.sendKeys(repayment);
        blurInputField(repaymentInputField);
    }

    public boolean isRepaymentErrorMessageDisplayed() {
        reporter.info("Checking if repayment error message is displayed.");
        ElementActions.waitForElementToBeDisplayed(repaymentErrorMessage, driver);
        return repaymentErrorMessage.isDisplayed();
    }

    public void clickOnDiscountCheckbox() {
        reporter.info("Click on discount checkbox");
        scrollToElement(discountCheckbox);
        discountCheckbox.click();
    }

    public void clickOnCalculationButton() {
        reporter.info("Click on calculation button");
        scrollToElement(calculationButton);
        ElementActions.waitForElementToBeClickable(calculationButton, driver);
        calculationButton.click();
    }

    public boolean isCalculationResultDisplayed() {
        reporter.info("Checking if calculation result is displayed.");
        ElementActions.waitForElementToBeDisplayed(calculationResult, driver);
        return calculationResult.isDisplayed();
    }

    public String getFirstMaximumLoanAmountIfItIsDisplayed() {
        reporter.info("Checking if maximum loan amount is displayed.");
        ElementActions.waitForElementToBeDisplayed(firstMaximumLoanAmount, driver);
        return firstMaximumLoanAmount.getText();
    }

    public void clickOnInsuranceDiscountCheckbox() {
        reporter.info("Click on insurance discount checkbox");
        scrollToElement(insuranceDiscountCheckbox);
        insuranceDiscountCheckbox.click();
    }

    public String getFirstThmIfItIsDisplayed() {
        reporter.info("Checking if first thm is displayed.");
        ElementActions.waitForElementToBeDisplayed(firstThm, driver);
        return firstThm.getText();
    }

    public void clearInputFields() {
        propertyValueInputField.clear();
        salaryInputField.clear();
        repaymentInputField.clear();
        ageInputField.clear();
    }

    // Helper JS executor methods

    private void blurInputField(WebElement inputField) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].dispatchEvent(new Event('blur'))", inputField);
    }

    public void simulateClickOnPropertyInputFieldToRefreshCalculationButton() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus();", propertyValueInputField);
        js.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true}));", propertyValueInputField);
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                element
        );
    }

}
