package org.rws.mastermind.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * The HttpHandlerImp class implements the HttpHandler interface
 * and provides a method to perform HTTP GET requests.
 */
public class HttpHandlerImp implements HttpHandler {
    private HttpURLConnection connection;

    /**
     * Performs an HTTP GET request to the specified URL.
     *
     * @param url The URL to send the GET request to.
     * @return The response from the GET request as a string.
     * @throws Exception If an error occurs during the HTTP request.
     */
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

    /**
     * Closes the connection to the server.
     */
    public void cleanup(){
        if (connection != null) {
            connection.disconnect();
        }
    }
    
}
