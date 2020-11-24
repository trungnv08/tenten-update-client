package com.trung.util;

import com.google.gson.Gson;
import com.trung.entity.Domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author trung
 * @version 1.0
 * @since 10/4/2020
 */

public class Helpers {
    public static final Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger(Helpers.class.getCanonicalName());

    static {
        logger.addHandler(LoggerHandler.getInstance());
    }

    private Helpers() {
    }

    /**
     * read properties file to list domain
     *
     * @return List Domain
     */
    public static List<Domain> getListPage(String propertyPath) {

        try (InputStream inputStream = new FileInputStream(propertyPath)) {
            Properties prop = new Properties();
            prop.load(inputStream);
            Domain[] domains = gson.fromJson(String.valueOf(prop.getProperty("pages")), Domain[].class);
            return Arrays.asList(domains);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Sorry, unable to find config.properties");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception fire!\n {0} ", e.getMessage());
        }
        return Collections.emptyList();
    }


    public static boolean validateIp(String ip) {
        String pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(pattern);
    }

    @SuppressWarnings("unused")
    public static <T extends Enum<T>> T lookup(Class<T> c, String string) {
        Objects.requireNonNull(c);
        try {
            return Enum.valueOf(Objects.requireNonNull(c), Objects.requireNonNull(string).trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Enum.valueOf(c, "ERROR");
        }

    }
}

