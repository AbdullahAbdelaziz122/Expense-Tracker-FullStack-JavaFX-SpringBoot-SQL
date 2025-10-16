package org.example.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiUtil {

    private static final String SPRINGBOOT_URL = "http://localhost:8080";

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    /**
     * Makes an HTTP request to the backend and returns the JSON response.
     *
     * @param apiPath       The API endpoint path (e.g. "/api/users/login")
     * @param requestMethod The HTTP method (GET, POST, etc.)
     * @param jsonData      Optional JSON payload for POST/PUT (can be null)
     * @return JsonObject   Response body parsed as JSON
     * @throws IOException  If network or IO error occurs
     */
    public static JsonObject fetchApi(String apiPath, RequestMethod requestMethod, JsonObject jsonData) throws IOException {
        HttpURLConnection connection = null;

        try {
            // Build the URL and open connection
            URL url = new URL(SPRINGBOOT_URL + apiPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod.toString());
            connection.setRequestProperty("Accept", "application/json");

            // Send data if necessary
            if (jsonData != null && requestMethod != RequestMethod.GET) {
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input);
                }
            }

            // Read response (input stream if success, error stream otherwise)
            int status = connection.getResponseCode();
            InputStream responseStream = (status >= 200 && status < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            String responseText = readStream(responseStream);

            // Parse the response to JSON (safe parse)
            JsonObject jsonResponse;
            try {
                jsonResponse = JsonParser.parseString(responseText).getAsJsonObject();
            } catch (Exception e) {
                jsonResponse = new JsonObject();
                jsonResponse.addProperty("rawResponse", responseText);
            }

            // Add status code to response
            jsonResponse.addProperty("status", status);
            return jsonResponse;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Utility method to read InputStream as a String.
     */
    private static String readStream(InputStream stream) throws IOException {
        if (stream == null) return "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
