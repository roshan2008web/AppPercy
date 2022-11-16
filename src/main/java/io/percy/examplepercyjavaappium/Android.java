package io.percy.examplepercyjavaappium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import io.percy.appium.AppPercy;

public class Android {
    private static AppPercy percy;

    // Hub Url to connect to Automation session
    private static String HUB_URL = "http://hub.browserstack.com/wd/hub";

    public static void main(String[] args) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Browserstack specific capabiilities
        capabilities.setCapability("browserstack.user", "<USER>");
        capabilities.setCapability("browserstack.key", "<USER_AUTH_KEY>");
        capabilities.setCapability("browserstack.appium_version", "1.20.2");

        // App url we get post uploading in response
        capabilities.setCapability("app", "<APP_URL>");
        capabilities.setCapability("device", "Google Pixel 3");
        capabilities.setCapability("os_version", "9.0");
        capabilities.setCapability("project", "First Java Project");

        // Create sessioin
        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL(HUB_URL), capabilities);

        // Initialize AppPercy
        percy = new AppPercy(driver);

        // Take First Screenshot
        percy.screenshot("First Screenshot");

        // Find scrollable element
        AndroidElement element = (AndroidElement) driver.findElementByXPath("//*[@scrollable='true']");
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", element.getId());
        params.put("direction", "down");
        params.put("percent", 1);
        driver.executeScript("mobile: scrollGesture", params);

        // Take Second Screenshot post scrolling
        percy.screenshot("Second Screenshot");

        driver.quit();
    }
}
