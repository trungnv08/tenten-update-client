package com.trung.entity;


import com.google.gson.Gson;
import com.trung.exception.CannotDetectIpException;
import com.trung.util.Helpers;
import com.trung.util.LoggerHandler;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.trung.constant.Constant.*;

/**
 * @author trung
 * @version 1.0
 * @since 10/4/2020
 */
public class Client {

    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private String csrfToken = "";
    private final Gson gson = new Gson();
    private Map<String, String> cookies;
    private final Domain domain;
    static {
        logger.addHandler(LoggerHandler.getInstance());
    }
    public Client(Domain domain) {
        this.domain = domain;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getMyGlobalIP() throws CannotDetectIpException {
        for (String service : IP_SERVICE) {
            logger.log(Level.INFO, " Resolve ip from service: {0} ", service);
            try {
                Document document = Jsoup.connect(service).get();
                String ip = document.text();
                if (!Helpers.validateIp(ip)) {
                    logger.log(Level.INFO, " can not Resolve ip from service: {0} ", service);
                    continue;
                }
                return ip;
            } catch (IOException exception) {
                logger.log(Level.SEVERE, exception.getMessage());
            }
        }
        throw new CannotDetectIpException("can not fetch ip from all service.\n Please check internet connection and try again!");
    }


    public void login() {
        login(domain.getUsername(), domain.getPasswd());
    }

    public void login(String user, String pwd) {
        try {
            Response response = Jsoup.connect(URL_SITE).timeout(10000).method(Method.GET).execute();
            Response r = Jsoup.connect(LOGIN_API)
                    .data("username", user).data("password", pwd)
                    .cookies(response.cookies())
                    .method(Method.POST)
                    .execute();
            this.cookies = r.cookies();
        } catch (IOException var5) {
            logger.log(Level.SEVERE, null, var5);
        }
    }

    public void extractCsrfToken() {
        Document document;
        try {
            document = Jsoup.connect(URL_SITE).cookies(cookies).get();
            //<input class="dev_token_csrf" type="hidden" name="dev_token_csrf" value="db4572698b1e71a10653bc70727ad418">
            Elements elements = document.select(CSRF_HTML_TAG);
            if (!elements.isEmpty()) {
                this.csrfToken = elements.first().val();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "error: {0}", e.getMessage());
        }


    }

    public void fetchSelfRecords() {
        this.domain.setRecords(fetchRecords());
    }

    public List<Record> fetchRecords() {
        List<Record> list = new ArrayList<>();

        try {
            Document document = Jsoup.connect(FETCH_API)
                    .data("type_get", "1")
                    .data("dev_token_csrf", this.csrfToken)
                    .cookies(cookies)
                    .post();
            ResponseData responseData = gson.fromJson(document.body().text(), ResponseData.class);
            list = Arrays.asList(new Gson().fromJson(responseData.getData(), Record[].class));
            list.forEach(e -> logger.log(Level.INFO, e.toString()));

        } catch (IOException var12) {
            logger.log(Level.SEVERE, null, var12);
        }

        return list;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    /**
     * //id_record: 905fdfd0-8329-49ac-87bb-e46ba99359c5
     * //type_record: A
     * //value_record_old: 113.179.184.111
     * //value_record_new: 113.179.184.112
     * //priority_record_new: 0
     * //priority_record_old: 0
     * //priority_srv:
     * //weight_srv:
     * //port_srv:
     * //value_srv:
     * //flag_caa:
     * //tag_caa:
     * //value_caa:
     * //dev_token_csrf: f2bfbf8bb26add4f26b620070a1b8789
     *
     * @param record record to update
     * @param newIp  new ip update
     * @return result of update execution true of false
     */
    public boolean updateRecord(Record record, String newIp) {
        boolean result = false;
        logger.log(Level.INFO, "[Client][UpdateRecord]: record to update: \n {0}", record);
        //check is same ip
        if (Arrays.asList(record.getRecords()).contains(newIp)) {
            logger.log(Level.INFO, "[Client][UpdateRecord]: old value({0}) and new value({1}) are same",
                    new Object[]{Arrays.deepToString(record.getRecords()), newIp});
            return false;
        }
        if (this.csrfToken.isEmpty()) {
            logger.log(Level.INFO, "[Client][UpdateRecord] csrf token is empty!");
        }
        try {
            Connection connection = Jsoup.connect(UPDATE_API)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data("id_record", record.getId())
                    .data("type_record", record.getType())
                    .data("value_record_old", record.getRecords()[0])
                    .data("value_record_new", newIp)
//                    .data("priority_record_new", "0")
//                    .data("priority_record_old", "0")
//                    .data("priority_srv", "")
//                    .data("weight_srv", "")
//                    .data("port_srv", "")
//                    .data("flag_caa", "")
//                    .data("tag_caa", "")
//                    .data("port_srv", "")
//                    .data("value_caa", "")
                    .data("data_init", gson.toJson(domain.getRecords()))
                    .data("dev_token_csrf", this.csrfToken)
                    .cookies(cookies)
                    .method(Method.POST);
            Response r = connection.execute();
            logger.log(Level.INFO, "response: {0} \n", r.body());
            result = r.body().trim().equals("1");
        } catch (IOException var5) {
            logger.log(Level.SEVERE, null, var5);
        }
        return result;
    }

    public void updateAllIpRecord(String ip) {
        this.domain.getRecords().parallelStream().filter(e -> e.getType().equals("A"))
//                .peek(e -> logger.log(Level.INFO,"update record: " + e))
                .forEach(rec -> logger.log(Level.INFO, " update record: {0}, result= {1}", new Object[]{rec, this.updateRecord(rec, ip)}));
    }

}

