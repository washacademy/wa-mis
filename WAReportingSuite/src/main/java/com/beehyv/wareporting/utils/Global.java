package com.beehyv.wareporting.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by beehyv on 2/6/17.
 */

public  final class Global {

    public static Integer[] retrieveCourseIdsFromFileLocationProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        String courseIdsString = null;
        try {
            input = new FileInputStream("../webapps/WAReportingSuite/WEB-INF/classes/app.properties");
            // load a properties file
            prop.load(input);
            courseIdsString = prop.getProperty("courseIds");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] courses = courseIdsString.split(",");
        Integer [] courseIds = new Integer[courses.length];
        for (int i =0;i<courses.length;i++){
            courseIds[i] = Integer.parseInt(courses[i]);
        }
        return courseIds;
    }


    public static String retrieveDocuments() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileLocation = null;
        try {
            File file = new File("../webapps/WAReportingSuite/WEB-INF/classes/app.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            fileLocation = prop.getProperty("fileLocation");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileLocation;
    }

    public static String retrieveUiAddress() {
        Properties prop = new Properties();
        InputStream input = null;
        String uiAddress = null;
        try {
            File file = new File("../webapps/WAReportingSuite/WEB-INF/classes/app.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            uiAddress = prop.getProperty("uiAdd");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uiAddress;
    }

    public static boolean isAutoGenerate() {
        Properties prop = new Properties();
        InputStream input = null;
        boolean uiAddress = false;
        try {
            File file = new File("../webapps/WAReportingSuite/WEB-INF/classes/app.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            uiAddress =  prop.getProperty("cron").equals("1");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uiAddress;
    }

}