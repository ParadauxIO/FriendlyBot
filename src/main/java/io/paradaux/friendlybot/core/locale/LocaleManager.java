package io.paradaux.friendlybot.core.locale;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Utility to loading and holding singleton instances of the ResourceBundle which serves as the locale/internationalisation system for the
 * plugin.
 * @author Rían Errity
 * */
public class LocaleManager {

    private static final File locale = new File("");

    private static ResourceBundle bundle;

    public LocaleManager() {
        if (!locale.exists()) {

        }

        try (FileInputStream fis = new FileInputStream(locale)) {
            bundle = new PropertyResourceBundle(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (!bundle.containsKey(key)) {
            return key;
        }

        return bundle.getString(key);
    }
}