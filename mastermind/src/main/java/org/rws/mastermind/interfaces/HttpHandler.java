package org.rws.mastermind.interfaces;

/**
 * The HttpHandler interface provides a method to perform HTTP GET requests.
 */
public interface HttpHandler {

    /**
     * Performs an HTTP GET request to the specified URL.
     *
     * @param url The URL to send the GET request to.
     * @return The response from the GET request as a string.
     * @throws Exception If an error occurs during the HTTP request.
     */
    String get(String url) throws Exception;
}
