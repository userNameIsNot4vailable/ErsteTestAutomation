package hu.robertszujo.seleniumproject.utils;

import hu.robertszujo.seleniumproject.constants.ElementConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementActions {

    public static void waitForElementToBeDisplayed(WebElement elementToBeDisplayed, WebDriver driver) {
        new WebDriverWait(driver, ElementConstants.MAX_WAIT_DURATION)
                .until(ExpectedConditions.visibilityOf(elementToBeDisplayed));
    }

    public static void waitForElementToDisappear(WebElement elementToDisappear, WebDriver driver) {
        new WebDriverWait(driver, ElementConstants.MAX_WAIT_DURATION)
                .until(ExpectedConditions.invisibilityOf(elementToDisappear));
    }

    public static void waitForElementToBeClickable(WebElement elementToBeClickable, WebDriver driver) {
        new WebDriverWait(driver, ElementConstants.MAX_WAIT_DURATION)
                .until(ExpectedConditions.elementToBeClickable(elementToBeClickable));
    }

}
