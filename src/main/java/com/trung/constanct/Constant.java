package com.trung.constanct;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author trung
 * @version 1.0
 * @since 11/18/2020
 */
public class Constant {
    private Constant() {
    }

    public static final List<String> IP_SERVICE = Collections.unmodifiableList(Arrays.asList(
            "http://ipecho.net/plain/",
            "http://ipinfo.io/ip",
            "http://ipconf.cf/"));
    public static final String CSRF_HTML_TAG = "input.dev_token_csrf";
    public static final String URL_SITE = "https://domain.tenten.vn/";
    public static final String API_URL = "https://domain.tenten.vn/ApiDnsSetting/";
    public static final String LOGIN_API = URL_SITE + "login";
    public static final String FETCH_API = API_URL + "getDataDns/";
    public static final String UPDATE_API = API_URL + "editDns/";
    public static final String PROPERTY_PATH="/home/orangepi/navi-ota/config.properties";
}
