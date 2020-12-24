package service;

import java.util.ResourceBundle;

public abstract class TestDataReader {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(System.getProperty("environment"));

    public static String getEnvironmentData(String key) { return resourceBundle.getString(key); }
}
