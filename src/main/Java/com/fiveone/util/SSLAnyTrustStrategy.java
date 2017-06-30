package com.fiveone.util;

import org.apache.http.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLAnyTrustStrategy implements TrustStrategy {

    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return true;
    }

}
