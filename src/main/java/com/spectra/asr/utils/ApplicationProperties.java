/* =================================================================== */
/* © 2015 Fresenius Medical Care Holdings, Inc. All rights reserved. */
/* =================================================================== */
package com.spectra.asr.utils;

import com.spectra.scorpion.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

//import org.apache.logging.log4j.LogManager;

/**
 * ApplicationProperties class is used to load the properties file only once.
 */
@Slf4j
public final class ApplicationProperties {
	//private static Logger log = Logger.getLogger(ApplicationProperties.class);
	
    /**
     * Holds the Application Properties ResourceBundle Name.
     */
    private static final String APPLICATIONPROPERTIES = "ApplicationProperties";

    /**
     * Holds the Application Properties file path .
     */
    private static final String APPLICATION_PROPERTIES_PATH = 
        "properties//testApplicationProperties.properties";

    /**
     * Holds the Application Properties.
     */
    static Properties properties = new Properties();

    /**
     * Holds the value whether the Application Properties are from
     * ResourceBundle or properties folder.
     */
    static boolean isFromProperties = false;

    /**
     * For non instantiation.
     */
    private ApplicationProperties() {
    }

    /**
     * Holds an instance of ResourceBundle.
     */
    private static ResourceBundle resourceBundle;
    static {

        try {
            File file = new File(APPLICATION_PROPERTIES_PATH);
            if (file.exists()) {
            	log.warn("static block: file: " + (file == null ? "NULL" : file.toString()));
                properties.load(new FileInputStream(file));
                log.warn("static block: properties: " + (properties == null ? "NULL" : properties.toString()));
                isFromProperties = true;
            } else {
                resourceBundle = ResourceBundle
                        .getBundle(APPLICATIONPROPERTIES);
                log.warn("static block: resourceBundle: " + (resourceBundle == null ? "NULL" : resourceBundle.toString()));
            }
        } catch (IOException e) {
            resourceBundle = ResourceBundle.getBundle(APPLICATIONPROPERTIES);
        }

    }

    /**
     * Retrieves the associated value for the specified key from the
     * ApplicationProperties file.
     * 
     * @param propertyName Holds the key in the property file.
     * @return Returns the associated value.
     */
    public static String getProperty(final String propertyName) {
    	log.warn("getProperty(): propertyName: " + (propertyName == null ? "NULL" : propertyName));
        if (isFromProperties) {
            return StringUtil.valueOf(properties.getProperty(propertyName))
                    .trim();
        } else {
        	String prop = resourceBundle.getString(propertyName);
        	log.warn("getProperty(): prop: " + (prop == null ? "NULL" : prop));
            return StringUtil.valueOf(resourceBundle.getString(propertyName))
                    .trim();
        }
    }
}
