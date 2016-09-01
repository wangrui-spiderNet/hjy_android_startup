package com.socks.okhttp.utils;

import javax.net.ssl.SSLSession;

/**
 * 信任地址
 */
public class HostnameVerifier implements javax.net.ssl.HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
