package hu.robertszujo.seleniumproject;

import com.aventstack.extentreports.ExtentTest;
import hu.robertszujo.seleniumproject.constants.TestConstants;
import hu.robertszujo.seleniumproject.constants.TestContextConstants;
import hu.robertszujo.seleniumproject.pages.LoanCalculatorPage;
import hu.robertszujo.seleniumproject.pages.components.CookiePopup;
import org.assertj.core.api.Assertions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class LoanCalculatorTests extends BaseTestClass {

    private ExtentTest reporter;

    // Page objects
    private LoanCalculatorPage loanCalculatorPage;
    private CookiePopup cookiePopup;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context, ITestResult result) {
        reporter = SuiteWideStorage.testReport.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        context.setAttribute(TestContextConstants.REPORTER, reporter);
        initializePageObjects();
        driver.get(TestConstants.CALCULATOR_PAGE_URL);
    }

    public void initializePageObjects() {
        loanCalculatorPage = new LoanCalculatorPage(driver, reporter);
        cookiePopup = new CookiePopup(driver, reporter);
    }

    // *** Tests ***

    @Test(description = "Cookie popup should be displayed after page load")
    public void loadCalculatorPage_cookiePopupShouldBeDisplayed() {
        // Given + When user loads calculator page

        // Then
        Assertions.assertThat(cookiePopup.isCookiePopupDisplayedAfterWaiting())
                .as("Cookie popup should have displayed after page load")
                .isTrue();
        reporter.pass("Cookie popup was displayed successfully");
    }

    @Test(description = "Cookie popup should disappear after accepting cookies")
    public void acceptCookies_CookiePopupShouldDisappear() {
        //Given
        cookiePopup.waitForCookiePopupToBeDisplayed();

        //When
        cookiePopup.clickOnCookieAcceptButton();

        //Then
        Assertions.assertThat(cookiePopup.hasCookiePopupDisappearedAfterWaiting())
                .as("Cookie popup should have disappeared")
                .isTrue();
        reporter.pass("Cookie popup has disappeared successfully");
    }
    
    @Test(description = "Calculator form should be displayed after page load & accepting cookies")
    public void loadPageAndAcceptCookies_CalculatorFormShouldBeDisplayed() {
        //Given + When
        loadPageAndAcceptCookies();
        
        //Then
        loanCalculatorPage.isCalculatorFormDisplayedAfterWaiting();
        reporter.pass("Loan calculator page was displayed successfully");
    }

    @Test(description = "Calculator form should not accept age less than 18")
    public void CalculatorFormShouldNotAcceptAgeLessThanEighteen() {
        //Given
        loadPageAndAcceptCookies();

        //When
        loanCalculatorPage.typeIntoAgeInputField("17");

        //Then
        Assertions.assertThat(loanCalculatorPage.isAgeErrorMessageDisplayed())
                .as("Age error message should be displayed")
                .isTrue();
        reporter.pass("Age error message was displayed successfully");
    }

    @Test(description = "Calculator form should not accept age bigger than 65")
    public void CalculatorFormShouldNotAcceptAgeBiggerThanSixtyFive() {
        //Given
        loadPageAndAcceptCookies();

        //When
        loanCalculatorPage.typeIntoAgeInputField("66");

        //Then
        Assertions.assertThat(loanCalculatorPage.isAgeErrorMessageDisplayed())
                .as("Age error message should be displayed")
                .isTrue();
        reporter.pass("Age error message was displayed successfully");
    }

    @Test(description = "Calculator form should not accept property value less than five million HUF")
    public void CalculatorFormShouldNotAcceptPropertyValueLessThanFiveMillion() {
        //Given
        loadPageAndAcceptCookies();

        //When
        loanCalculatorPage.typeIntoPropertyValueInputField("4999999");

        //Then
        Assertions.assertThat(loanCalculatorPage.isPropertyValueErrorMessageDisplayed())
                .as("Property value error message should be displayed")
                .isTrue();
        reporter.pass("Property value error message was displayed successfully");
    }

    @Test(description = "Calculator form should not accept salary less than 193 000 HUF when alone radio button is clicked")
    public void CalculatorFormShouldNotAcceptSalaryLessThanOneHundredNinetyThreeThousand() {
        //Given
        loadPageAndAcceptCookies();

        //When
        loanCalculatorPage.clickOnAloneRadioButton();

        //And
        loanCalculatorPage.typeIntoSalaryInputField("192999");

        //Then
        Assertions.assertThat(loanCalculatorPage.getSalaryErrorMessageIfItIsDisplayed())
                .as("Salary error message should be displayed and should be correct")
                .isEqualTo("Minimum 193 000 ft jövedelem szükséges");

        reporter.pass("Salary error message was displayed correctly");
    }

    @Test(description = "Calculator form should not accept salary less than 290 000 HUF when several radio button is clicked")
    public void CalculatorFormShouldNotAcceptSalaryLessThanTwoHundredNinetyThousand() {
        //Given
        loadPageAndAcceptCookies();

        //When
        loanCalculatorPage.clickOnSeveralRadioButton();

        //And
        loanCalculatorPage.typeIntoSalaryInputField("289999");

        //Then
        Assertions.assertThat(loanCalculatorPage.getSalaryErrorMessageIfItIsDisplayed())
                .as("Salary error message should be displayed and should be correct")
                .isEqualTo("Minimum 290 000 ft jövedelem szükséges");

        reporter.pass("Salary error message was displayed correctly");
    }

    @Test(description = "Calculator form should not accept repayment bigger than 50 percent when salary is under 800 000 HUF")
    public void CalculatorFormShouldNotAcceptRepaymentBiggerThanFiftyPercent_WhenSalaryIsUnderEightHundredThousandHuf() {
        //Given
        loadPageAndAcceptCookies();

        //When
        loanCalculatorPage.typeIntoSalaryInputField("500000");

        //And
        loanCalculatorPage.typeIntoRepaymentInputField("300000");

        //Then
        Assertions.assertThat(loanCalculatorPage.isRepaymentErrorMessageDisplayed())
                .as("Repayment error message should be displayed")
                .isTrue();

        reporter.pass("Repayment error message was displayed successfully");
    }

    @Test(description = "Calculator form should not accept repayment bigger than 60 percent when salary is bigger than 800 000 HUF")
    public void CalculatorFormShouldNotAcceptRepaymentBiggerThanSixtyPercent_WhenSalaryIsBiggerThanEightHundredThousandHuf() {
        //Given
        loadPageAndAcceptCookies();

        //When
        loanCalculatorPage.typeIntoSalaryInputField("900000");

        //And
        loanCalculatorPage.typeIntoRepaymentInputField("600000");

        //Then
        Assertions.assertThat(loanCalculatorPage.isRepaymentErrorMessageDisplayed())
                .as("Repayment error message should be displayed")
                .isTrue();

        reporter.pass("Repayment error message was displayed successfully");
    }

    @Test(description = "Calculator form should accept valid data and calculation should be displayed")
    public void CalculatorFormShouldAcceptValidData_AndCalculationShouldBeDisplayed() {
        loadPageAndAcceptCookies();

        fillInputFields("50000000", "1000000", "500000");

        loanCalculatorPage.clickOnDiscountCheckbox();

        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();

        reporter.pass("Calculation result was displayed successfully");
    }

    @Test(description = "The calculated loan amount should increase as the property's value increases")
    public void CalculatedLoanAmountShouldIncreaseAsThePropertyValueIncreases() {
        int firstMaxLoanAmount = 0;
        int secondMaxLoanAmount = 0;

        loadPageAndAcceptCookies();
        fillInputFields("35000000", "600000", "100000");
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        firstMaxLoanAmount = Integer.parseInt(loanCalculatorPage.getFirstMaximumLoanAmountIfItIsDisplayed().replaceAll(" ", ""));

        loanCalculatorPage.clearInputFields();
        fillInputFields("55000000", "600000", "100000");
        loanCalculatorPage.simulateClickOnPropertyInputFieldToRefreshCalculationButton();
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        secondMaxLoanAmount = Integer.parseInt(loanCalculatorPage.getFirstMaximumLoanAmountIfItIsDisplayed().replaceAll(" ", ""));

        Assertions.assertThat(secondMaxLoanAmount).isGreaterThan(firstMaxLoanAmount);

        reporter.pass("Calculated loan amount increased as intended");
    }

    @Test(description = "The calculated loan amount should increase as the salary increases")
    public void CalculatedLoanAmountShouldIncreaseAsTheSalaryIncreases() {
        int firstMaxLoanAmount = 0;
        int secondMaxLoanAmount = 0;

        loadPageAndAcceptCookies();
        fillInputFields("95000000", "600000", "100000");
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        firstMaxLoanAmount = Integer.parseInt(loanCalculatorPage.getFirstMaximumLoanAmountIfItIsDisplayed().replaceAll(" ", ""));

        loanCalculatorPage.clearInputFields();
        fillInputFields("95000000", "1200000", "100000");
        loanCalculatorPage.simulateClickOnPropertyInputFieldToRefreshCalculationButton();
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        secondMaxLoanAmount = Integer.parseInt(loanCalculatorPage.getFirstMaximumLoanAmountIfItIsDisplayed().replaceAll(" ", ""));

        Assertions.assertThat(secondMaxLoanAmount).isGreaterThan(firstMaxLoanAmount);

        reporter.pass("Calculated loan amount increased as intended");
    }

    @Test(description = "The calculated loan amount should decrease as the repayment increases")
    public void CalculatedLoanAmountShouldDecreaseAsTheRepaymentIncreases() {
        int firstMaxLoanAmount = 0;
        int secondMaxLoanAmount = 0;

        loadPageAndAcceptCookies();
        fillInputFields("95000000", "600000", "100000");
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        firstMaxLoanAmount = Integer.parseInt(loanCalculatorPage.getFirstMaximumLoanAmountIfItIsDisplayed().replaceAll(" ", ""));

        loanCalculatorPage.clearInputFields();
        fillInputFields("95000000", "600000", "300000");
        loanCalculatorPage.simulateClickOnPropertyInputFieldToRefreshCalculationButton();
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        secondMaxLoanAmount = Integer.parseInt(loanCalculatorPage.getFirstMaximumLoanAmountIfItIsDisplayed().replaceAll(" ", ""));

        Assertions.assertThat(secondMaxLoanAmount).isLessThan(firstMaxLoanAmount);

        reporter.pass("Calculated loan amount decreased as intended");
    }

    @Test(description = "The calculated thm should decrease when insurance is used")
    public void CalculatedThmShouldDecreaseWhenInsuranceIsUsed() {
        double firsThm = 0;
        double secondThm = 0;

        loadPageAndAcceptCookies();
        fillInputFields("95000000", "600000", "100000");
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        firsThm = Double.parseDouble(
                loanCalculatorPage.getFirstThmIfItIsDisplayed().replace("%", "")
                        .replace(",", ".")
        );

        loanCalculatorPage.clickOnInsuranceDiscountCheckbox();
        loanCalculatorPage.simulateClickOnPropertyInputFieldToRefreshCalculationButton();
        loanCalculatorPage.clickOnCalculationButton();

        Assertions.assertThat(loanCalculatorPage.isCalculationResultDisplayed())
                .as("Calculation result should be displayed")
                .isTrue();
        secondThm = Double.parseDouble(
                loanCalculatorPage.getFirstThmIfItIsDisplayed().replace("%", "")
                        .replace(",", ".")
        );

        Assertions.assertThat(secondThm).isLessThan(firsThm);

        reporter.pass("Calculated thm decreased as intended");
    }

    // *** Helper methods ***
    
    private void loadPageAndAcceptCookies() {
        cookiePopup.waitForCookiePopupToBeDisplayed();
        cookiePopup.clickOnCookieAcceptButton();
        cookiePopup.waitForCookiePopupToDisappear();
    }


    private void fillInputFields(String propertyValue, String salary, String repayment) {
        loanCalculatorPage.typeIntoPropertyValueInputField(propertyValue);
        loanCalculatorPage.typeIntoAgeInputField("27");
        loanCalculatorPage.typeIntoSalaryInputField(salary);
        loanCalculatorPage.typeIntoRepaymentInputField(repayment);
    }
}
