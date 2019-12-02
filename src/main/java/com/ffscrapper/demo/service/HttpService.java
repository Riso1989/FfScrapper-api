package com.ffscrapper.demo.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpService {

    public static boolean pingURL(String url, int timeout) {
        url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

    public static void setProxy() {
        System.setProperty("https.proxyHost", "proxy.t-systems.sk");
        System.setProperty("https.proxyPort", "3128");
        System.setProperty("http.proxyHost", "proxy.t-systems.sk");
        System.setProperty("http.proxyPort", "3128");
    }
}
