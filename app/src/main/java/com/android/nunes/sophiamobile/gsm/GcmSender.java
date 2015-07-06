/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.nunes.sophiamobile.gsm;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GcmSender {

    public static final String API_KEY = "AIzaSyB8ytfj6SsItOagXw6jI1Jms8hdxnCRL4g";

    public static void enviar(String msg, String token) {

        try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
            jData.put("message", msg.trim());
            // Where to send GCM message.
            if (token != null) {
                jGcmData.put("to", token.trim());
            } else {
                jGcmData.put("to", "/topics/global");
            }
            // What to send in GCM message.
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            System.out.println("Check your device/emulator for notification or logcat for " +
                    "confirmation of the receipt of the GCM message.");
        } catch (IOException e) {
            System.out.println("Unable to send GCM message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
