package com.source.utils.train;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class myHostnameVerifier implements HostnameVerifier {
    public void checkClientTrusted(X509Certificate[] chain, String authType) {}

    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[] {};
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String, javax.net.ssl.SSLSession)
     */
    @Override
    public boolean verify(String arg0, SSLSession arg1) {
        // TODO Auto-generated method stub
        return false;
    }
}