package io.percy.examplepercyappiumjava;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.ios.IOSDriver;
import io.percy.appium.AppPercy;

public class Ios {
    private static AppPercy percy;

    // Hub Url to connect to Automation session
    private static String HUB_URL = "https://hub.browserstack.com/wd/hub";

    public static void main(String[] args) throws MalformedURLException {

        IOSDriver driver = getIosDriver();

        // Initialize AppPercy
        percy = new AppPercy(driver);

        // Take First Screenshot
        percy.screenshot("First Screenshot");

        // Find element and click to change screen
        WebElement textButton = new WebDriverWait(driver, Duration.ofSeconds(30)).until(
            ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Text Button")));
        textButton.click();

        // Find textInput and send some data to it
        WebElement textInput =  new WebDriverWait(driver, Duration.ofSeconds(30)).until(
            ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Text Input")));
        textInput.sendKeys("hello@percy.io\n");

        // Take Second Screenshot Post screen update
        percy.screenshot("Second Screenshot");
        driver.quit();
    }

    private static IOSDriver getIosDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("projectName", "Demo Project");
        browserstackOptions.put("appiumVersion", "1.21.0");
        browserstackOptions.put("local", "true");
        capabilities.setCapability("bstack:options", browserstackOptions);
        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("platformVersion", "16");
        capabilities.setCapability("deviceName", "iPhone 14");
        capabilities.setCapability("app", "bs://59ef24b43c01e7eb336bc95026cf405fe11f60bc");

        // Percy Options
        capabilities.setCapability("percy.enabled", "true");
        capabilities.setCapability("percy.ignoreErrors", "true");

        // Create session
        String browserstackUsername = System.getenv("BROWSERSTACK_USERNAME");
        String browserstackAccessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

        IOSDriver driver = new IOSDriver(new URL("http://" + browserstackUsername + ":" + browserstackAccessKey + "@hub.browserstack.com/wd/hub"), capabilities);
        return driver;
    }
}
