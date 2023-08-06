package com.virtus.translate;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class Translator {

    private static final String ROOT_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final String BUNDLES_CONFIG_PATH = ROOT_PATH + "messages.properties";
    private static final Properties BUNDLES_PROPERTIES = new Properties();

    public static String translate(String key) {
        try {
            BUNDLES_PROPERTIES.load(new FileInputStream(BUNDLES_CONFIG_PATH));
            return BUNDLES_PROPERTIES.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return key;
        }
    }

    public static String translate(String key, String... params) {
        try {
            BUNDLES_PROPERTIES.load(new FileInputStream(BUNDLES_CONFIG_PATH));
            String translation = BUNDLES_PROPERTIES.getProperty(key);
            if (translation != null) {
                return MessageFormat.format(translation, params);
            }
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return key;
        }
    }

}
