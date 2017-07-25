/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.example.nilay.myapplication.backend;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import org.apache.commons.io.IOUtils;
/**
 * This class is used to send GCM downstream messages in the same way a server would.
 */
public class GcmServer {

    private static final String GCM_SEND_ENDPOINT = "https://fcm.googleapis.com/fcm/send";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String UTF8 = "UTF-8";
    private static final String PARAM_TO = "to";
    private static final String PARAM_COLLAPSE_KEY = "collapse_key";
    private static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";
    private static final String PARAM_TIME_TO_LIVE = "time_to_live";
    private static final String PARAM_DRY_RUN = "dry_run";
    private static final String PARAM_RESTRICTED_PACKAGE_NAME = "restricted_package_name";

    private static final String PARAM_PLAINTEXT_PAYLOAD_PREFIX = "data.";
    private static final String PARAM_JSON_NOTIFICATION_PARAMS = "notification";

    public static final String RESPONSE_PLAINTEXT_MESSAGE_ID = "id";
    public static final String RESPONSE_PLAINTEXT_CANONICAL_REG_ID = "registration_id";
    public static final String RESPONSE_PLAINTEXT_ERROR = "Error";
    private static final String PARAM_JSON_PAYLOAD = "data";
    private int responseCode;
    private String responseBody;


    //String data = "{data:\"\"name\":\"Nilay\",\"email\":\"nilay175@gmail.com}";
    private final String key;
    //private final LoggingService.Logger logger;
    /**
     * @param key    The API key used to authorize calls to Google
     *
     */
    public GcmServer(String key) {
        this.key = key;
        //this.logger = logger;
    }
    /**
     * Adds a new parameter to the HTTP POST body without doing any encoding.
     *
     * @param body  HTTP POST body.
     * @param name  parameter's name.
     * @param value parameter's value.
     */
    private static void addOptParameter(StringBuilder body, String name, Object value) {
        if (value != null) {
            String encodedValue = value.toString();
            if (value instanceof Boolean) {
                encodedValue = ((Boolean) value) ? "1" : "0";
            }
            body.append('&').append(name).append('=').append(encodedValue);
        }
    }

    public static void main(String[] args) {
        try {
            createJson();
           // doPost(GCM_SEND_ENDPOINT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //converttojson();

    }

    public static void doPost(String url) throws IOException {
        // Log.i(LoggingService.LOG_TAG, "HTTP request. body: " + requestBody);
        byte[] outputBytes = "{\"data\":{\"title\": \"My title\", \"text\": \"My text\", \"sound\": \"default\"}, \"to\": \"d2FVdpciVsU:APA91bEUf93XfCWfzq4yhthJt6d7bzf5LQx7RKK0xnZ_JVJlbF2IYr79uUQWROdsUsABHDeMVka041GIK9oF_RFsInnJLQk07Gs2aeRj6Zazz8cxAONi50E7w7vf0olLP_tW-sszFHIC\"}".getBytes();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(PARAM_TO, "d2FVdpciVsU:APA91bEUf93XfCWfzq4yhthJt6d7bzf5LQx7RKK0xnZ_JVJlbF2IYr79uUQWROdsUsABHDeMVka041GIK9oF_RFsInnJLQk07Gs2aeRj6Zazz8cxAONi50E7w7vf0olLP_tW-sszFHIC");
            // jsonBody.putOpt(PARAM_COLLAPSE_KEY, message.getCollapseKey());
            // jsonBody.putOpt(PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
            jsonBody.put(PARAM_JSON_PAYLOAD, "nilay");
            jsonBody.putOpt(PARAM_TIME_TO_LIVE, 30);

            //jsonBody.putOpt(PARAM_DELAY_WHILE_IDLE, message.isDelayWhileIdle());
            // jsonBody.putOpt(PARAM_DRY_RUN, message.isDryRun());

            /*if (message.getData().size() > 0) {
                JSONObject jsonPayload = new JSONObject(message.getData());
                jsonBody.put(PARAM_JSON_PAYLOAD, jsonPayload);
            }
            if (message.getNotificationParams().size() > 0) {
                JSONObject jsonNotificationParams = new JSONObject(message.getNotificationParams());
                jsonBody.put(PARAM_JSON_NOTIFICATION_PARAMS, jsonNotificationParams);
            }*/
        } catch (JSONException e) {
            //logger.log(Log.ERROR, "Failed to build JSON body");
            throw new IOException("Failed to build JSON body");
        }

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty(HEADER_AUTHORIZATION,"key="+"AIzaSyBx4J3ZSO66uGfkEmXHuwnRNPJTo7t_YKM");
        conn.setRequestProperty(HEADER_CONTENT_TYPE,CONTENT_TYPE_JSON);
        conn.setUseCaches(false);
        //conn.setFixedLengthStreamingMode(requestBody.getBytes().length);
        conn.setRequestMethod("POST");
        OutputStream out = null;
        try {
            out = conn.getOutputStream();
            out.write(outputBytes);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }
        int  responseCode = conn.getResponseCode();
        System.out.print(responseCode);
        InputStream inputStream = conn.getInputStream();
        String resp = IOUtils.toString(inputStream);
        System.out.println(resp);
        //  Log.i(LoggingService.LOG_TAG, "HTTP response. body: " + responseBody);
        conn.disconnect();
    }
    /**
     * Convenience method to convert an InputStream to a String.
     * <p/>
     * If the stream ends in a newline character, it will be stripped.
     * <p/>
     * If the stream is {@literal null}, returns an empty string.
     */
    private static String getString(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(stream));
        StringBuilder content = new StringBuilder();
        String newLine;
        do {
            newLine = reader.readLine();
            if (newLine != null) {
                content.append(newLine).append('\n');
            }
        } while (newLine != null);
        if (content.length() > 0) {
            // strip last newline
            content.setLength(content.length() - 1);
        }
        return content.toString();
    }

    private static void createJson()throws IOException {
        JSONObject jsonBody = new JSONObject();
        try {
            JSONObject data = new JSONObject();
            data.put("name","nilay");
            data.put("email","nilay175@gmail.com");
            jsonBody.put("data",data);
            jsonBody.put(PARAM_TO, "APA91bHEozs1fGe3r7VIbeY19POPs1AroteG-3jRaB1c9s02kkuSTe6RdgK3r3HXVtuw-y4jizxqNWz683BmyzZzpwOzprnNsXAocbx2-Kh0Y6VXO_w4WM2tW8_bXWrtjOnBXI9ilS08");
            // jsonBody.putOpt(PARAM_COLLAPSE_KEY, message.getCollapseKey());
            // jsonBody.putOpt(PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
            jsonBody.putOpt(PARAM_TIME_TO_LIVE, 30);
        }
        catch (JSONException e) {
            //logger.log(Log.ERROR, "Failed to build JSON body");
            throw new IOException("Failed to build JSON body");
        }
        String str = jsonBody.toString();
        System.out.println("String "+str);
    }
}