package com.ivr.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;

import com.ivr.config.RestClientProperties;

public class Client {

    /**
     * Requesting HTTP connection (failover in urlStrings)
     * 
     * @param urlStrings
     * @param requestMethod
     * @param param
     * @return
     * @throws Exception
     */
    public static String request(String urlStrings[], String requestMethod, String param) throws Exception {

        String result = "";
        int idx = 0;

        for (String url : urlStrings) {
            idx++;

            try {
                result = request(url, requestMethod, param);
                break; // Returns results if succeed
            } catch (Exception e) {
                if (idx == urlStrings.length) {
                    throw e;
                }
            }
        }

        return result;
    }

    /**
     * Requesting HTTP connection
     * 
     * @param urlString
     * @param requestMethod
     * @param param
     * @return
     * @throws IOException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    private static String request(String urlString, String requestMethod, String param)
            throws IOException, KeyManagementException, NoSuchAlgorithmException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        int timeout = RestClientProperties.getInstance().getInt("http.request.timeout.ms");

        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setRequestMethod(requestMethod);

        if ("GET".equals(requestMethod) || "DELETE".equals(requestMethod) || param.isEmpty()) {
            connection.setDoOutput(false);
        } else {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.getOutputStream().write(param.getBytes("UTF-8"));
        }

        ignoreTLSCertification();
        connection.connect();

        byte[] buffer = IOUtils.toByteArray(connection.getInputStream());

        return new String(buffer, "UTF-8");
    }

    /**
     * Ignore TLS certification on TLS handshaking
     * 
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static void ignoreTLSCertification() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostValid = new HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostValid);
    }
}
