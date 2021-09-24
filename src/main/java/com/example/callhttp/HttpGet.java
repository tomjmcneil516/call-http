package com.example.callhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpGet {

    public static final int EXPONENTIAL_BACKOFF_BASE_MS = 200;
    public static final int EXPONENTIAL_BACKOFF_MULTIPLIER = 2;
    public static final int TIMEOUT_MS = 500;
    public static final int RETRIES = 2;

    public static String runCommand(String urlToRead) throws IOException, InterruptedException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = null;

        for(int retry = 0; retry <= RETRIES; retry++){
            if (retry > 0) {
                int wait_interval = EXPONENTIAL_BACKOFF_BASE_MS * ( EXPONENTIAL_BACKOFF_MULTIPLIER ^ retry );
                Thread.sleep(wait_interval);
            }

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MS);

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                break;
            }
            if(retry == RETRIES){
                return conn.getResponseMessage();
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        return result.toString();
    }
}