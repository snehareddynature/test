package in.at.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {

    //protected static ThreadLocal<Properties> PROPS = new ThreadLocal<>();

    private static Properties properties = null;
    private static Properties configProp = getProperties(Constants.CONFIG_DIR, Constants.CONFIG_FILE_NAME);

    /*public static void setProps(Properties props) {
        PROPS.set(props);
    }*/

    /*public static Properties getProps() {
        return PROPS.get();
    }*/

    /**
     * @param FILE_PATH
     * @param FILE_NAME
     * @return
     */
    public static Properties getProperties(final String FILE_PATH, final String FILE_NAME) {
        File file = new File(FILE_PATH, FILE_NAME);
        Properties properties = null;
        try {
            properties = new Properties();
            // Reading the properties file
            properties.load(new FileInputStream(file));
        } catch (IOException fileNotFoundException) {
            System.err.println(fileNotFoundException);
        }
        return properties;
    }

    // It will copy/set all the properties from pom.xml file and environment(QA/DEV) properties file to config properties file
    public static Properties getProperties() {

        if (!System.getProperty("pomBrowser").equals("") && !System.getProperty("pomEnv").equals("")) {
            configProp.setProperty("browser", System.getProperty("pomBrowser"));
            configProp.setProperty("environment", System.getProperty("pomEnv"));
            configProp.setProperty("testType", System.getProperty("pomTestType"));
            configProp.setProperty("executionMode", System.getProperty("pomExec"));
        }
        if (configProp.getProperty("environment").equalsIgnoreCase("qa")) {
            properties = getProperties(Constants.CONFIG_DIR, Constants.QA_CONFIG_FILE_NAME);
        } else if (configProp.getProperty("environment").equalsIgnoreCase("dev")) {
            properties = getProperties(Constants.CONFIG_DIR, Constants.DEV_CONFIG_FILE_NAME);
        } else {
            System.exit(1);
        }
        for (Object key : properties.keySet()) {
            configProp.setProperty(key.toString(), properties.getProperty(key.toString()));
        }
        //setProps(configProp);
        return configProp;
    }

    public static String getPropValue(Properties properties, String key) {
        // Returning the Property value depends on Key
        return properties.getProperty(key);
    }

    public static void setProps(Properties properties, String key, String value) {
        // Setting the Properties Object
        properties.setProperty(key, value);
    }

    public static void clearProps(Properties properties) {
        // Clearing the Properties Object
        properties.clear();
    }
}