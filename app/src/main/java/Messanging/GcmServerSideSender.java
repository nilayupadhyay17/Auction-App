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
package Messanging;

import static Messanging.HttpRequest.CONTENT_TYPE_FORM_ENCODED;
import static Messanging.HttpRequest.CONTENT_TYPE_JSON;
import static Messanging.HttpRequest.HEADER_CONTENT_TYPE;
import static Messanging.HttpRequest.HEADER_AUTHORIZATION;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.net.URLEncoder;
import java.util.Map;

/**
 * This class is used to send GCM downstream messages in the same way a server would.
 */
public class GcmServerSideSender {

    private static final String GCM_SEND_ENDPOINT = "https://gcm-http.googleapis.com/gcm/send";

    private static final String UTF8 = "UTF-8";

    private static final String PARAM_TO = "to";
    private static final String PARAM_COLLAPSE_KEY = "collapse_key";
    private static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";
    private static final String PARAM_TIME_TO_LIVE = "time_to_live";
    private static final String PARAM_DRY_RUN = "dry_run";
    private static final String PARAM_RESTRICTED_PACKAGE_NAME = "restricted_package_name";

    private static final String PARAM_PLAINTEXT_PAYLOAD_PREFIX = "data.";

    private static final String PARAM_JSON_PAYLOAD = "data";
    private static final String PARAM_JSON_NOTIFICATION_PARAMS = "notification";

    public static final String RESPONSE_PLAINTEXT_MESSAGE_ID = "id";
    public static final String RESPONSE_PLAINTEXT_CANONICAL_REG_ID = "registration_id";
    public static final String RESPONSE_PLAINTEXT_ERROR = "Error";
    String data = "{data:\"\"name\":\"Nilay\",\"email\":\"nilay175@gmail.com}";


    private final String key;
    //private final LoggingService.Logger logger;


    /**
     * @param key    The API key used to authorize calls to Google
     *
     */
    public GcmServerSideSender(String key) {
        this.key = key;
        //this.logger = logger;
        }
    //byte[] outputBytes = "{\"notification\":{\"title\": \"My title\", \"text\": \"My text\", \"sound\": \"default\"}, \"to\": \"APA91bHEozs1fGe3r7VIbeY19POPs1AroteG-3jRaB1c9s02kkuSTe6RdgK3r3HXVtuw-y4jizxqNWz683BmyzZzpwOzprnNsXAocbx2-Kh0Y6VXO_w4WM2tW8_bXWrtjOnBXI9ilS08\"}".getBytes("UTF-8");

    /**
     * Send a downstream message via HTTP JSON.
     *
     * @param destination the registration id of the recipient app.
     * @throws IOException
     */
    public String sendHttpJsonDownstreamMessage(String destination) throws IOException {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(PARAM_TO, destination);
           // jsonBody.putOpt(PARAM_COLLAPSE_KEY, message.getCollapseKey());
           // jsonBody.putOpt(PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
            jsonBody.putOpt(PARAM_TIME_TO_LIVE, 30);
            //jsonBody.put(PARAM_JSON_PAYLOAD, jsonPayload);
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

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        httpRequest.setHeader(HEADER_AUTHORIZATION, "key=" + key);
        httpRequest.doPost(GCM_SEND_ENDPOINT, jsonBody.toString());

        if (httpRequest.getResponseCode() != 200) {
            throw new IOException("Invalid request."
                    + " status: " + httpRequest.getResponseCode()
                    + " response: " + httpRequest.getResponseBody());
        }

        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(httpRequest.getResponseBody());
            //logger.log(Log.INFO, "Send message:\n" + jsonResponse.toString(2));
        } catch (JSONException e) {
            /*logger.log(Log.ERROR, "Failed to parse server response:\n"
                    + httpRequest.getResponseBody());*/
        }
        return httpRequest.getResponseBody();
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
        converttojson();

    }

    private static void converttojson() {
        JSONObject jObject = new JSONObject();
        try {
            jObject.put("Nickname","nilay");
            jObject.put("id","nilay175@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //String jsontext = jObject.toString();
        System.out.print(jObject);
        System.out.print("hiii");
    }
}