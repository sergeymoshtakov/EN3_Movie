package ch.fhnw.oop2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The NetworkCommunication class handles the network communication with the server to fetch movie data.
 * It provides a method to asynchronously fetch data from the server and invoke a callback when the data is received.
 */
public class NetworkCommunication {

    /**
     * Fetches movie data from the server asynchronously and invokes the callback with the received JSON data.
     *
     * @param callback The callback to be invoked when the data is received from the server.
     */
    public void fetchDataFromServer(DataCallback callback) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://softwarelab.ch/api/public/v2/movies")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenAccept(jsonData -> {
                    callback.onDataReceived(jsonData);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    /**
     * The DataCallback interface provides a callback method to be implemented for receiving the fetched data.
     */
    interface DataCallback {
        /**
         * Called when the data is received from the server.
         *
         * @param jsonData The JSON data received from the server.
         */
        void onDataReceived(String jsonData);
    }
}
