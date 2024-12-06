package org.rws.mastermind.http;

import org.rws.mastermind.interfaces.HttpHandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class HttpHandlerImp implements HttpHandler {
    private HttpURLConnection connection;

    @Override
    public String get(String url) throws Exception {
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Check if the response code is 200 (OK)
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("HTTP GET request failed with error code: " + responseCode);
            }

            // Read the response from the server
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            }
        } finally {
            cleanup();
        }
    }   

    public void cleanup(){
        if (connection != null) {
            connection.disconnect();
        }
    }
    
}
