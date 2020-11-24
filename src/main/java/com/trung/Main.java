package com.trung;

import com.trung.constant.Constant;
import com.trung.entity.Client;
import com.trung.entity.Domain;
import com.trung.exception.CannotDetectIpException;
import com.trung.util.Helpers;
import com.trung.util.LoggerHandler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author trung
 * @version 1.0
 * @since 10/4/2020
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getCanonicalName());
    private static final String CMD_CONFIG = "-c";

    static {
        logger.addHandler(LoggerHandler.getInstance());
    }

    public static void main(String[] args) {
        try {
            String configPath = Constant.PROPERTY_PATH;
            for (Domain page : Helpers.getListPage(configPath)) {
                updateIpOfDomain(page);
            }
        } catch (Exception e) {
            logger.severe("error: " + e.getMessage());
            showMan();
        }
    }

    public static void updateIpOfDomain(Domain domain) {
        try {
            logger.log(Level.INFO, "-------- page: {0} -------------", domain.getUsername());
            Client client = new Client(domain);
            logger.log(Level.INFO, "login");
            client.login();
            logger.log(Level.INFO, "cookie: {0}", client.getCookies());
            logger.log(Level.INFO, "get token");
            client.extractCsrfToken();
            logger.log(Level.INFO, "token: {0} ", client.getCsrfToken());
            logger.log(Level.INFO, "fetch records");
            client.fetchSelfRecords();
            String ip = client.getMyGlobalIP();
            logger.log(Level.INFO, "current global ip: {0} ", ip);
            client.updateAllIpRecord(ip);
            logger.log(Level.INFO, "----------------------------------------");
        } catch (CannotDetectIpException e) {
            logger.log(Level.INFO, e.getMessage());
            logger.log(Level.INFO, "----------------------------------------");

        }
    }

    public static void showMan() {
        logger.log(Level.INFO, "man of updater: \n");
        logger.log(Level.INFO, " -h : show help.");
        logger.log(Level.INFO, "-c [config file]: run jar with config file");
        logger.log(Level.INFO, "example: app.jar -c config.properties");
    }

    @SuppressWarnings("unused")
    public static boolean validateCommand(List<String> cmdList) {
        return cmdList.size() == 2 && cmdList.get(0).equals(CMD_CONFIG);
    }
}
