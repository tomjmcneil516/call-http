package com.example.callhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


public class HttpGet {

    private static final int EXPONENTIAL_BACKOFF_BASE_MS = 200;
    private static final int EXPONENTIAL_BACKOFF_MULTIPLIER = 2;
    private static final int TIMEOUT_MS = 500;
    private static final int RETRIES = 2;

    public static String runCommand(String urlToRead) {

        StringBuilder result = new StringBuilder();
        URL url = null;

        try {
            url = new URL(urlToRead);
        } catch (MalformedURLException e) {
            return "Error: URL is not valid";
        }

        HttpURLConnection conn = null;

        for(int retry = 0; retry <= RETRIES; retry++){
            if (retry > 0) {
                int wait_interval = EXPONENTIAL_BACKOFF_BASE_MS * ( EXPONENTIAL_BACKOFF_MULTIPLIER ^ retry );
                try {
                    Thread.sleep(wait_interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(TIMEOUT_MS);

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    break;
                }
            } catch (IOException e) {
                if(retry == RETRIES){
                    return "Connection could not be established";
                }
            }
        }

        try  (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        } catch (IOException e) {
            return "Error: Could not read input stream";
        }

        return result.toString();
    }
}