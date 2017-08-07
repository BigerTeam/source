package com.source.utils.train;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class myX509TrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] chain, String authType) {}

    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[] {};
    }
}