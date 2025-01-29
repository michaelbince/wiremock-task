package utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockManager {
    private static WireMockServer wireMockServer;

    public static void startWireMock() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(options().port(8080));
            wireMockServer.start();
            WireMock.configureFor("localhost", 8080);
            registerStubs();
            System.out.println("WireMock server started on port 8080");
        }
    }

    public static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
            System.out.println("WireMock server stopped.");
        }
    }

    private static void registerStubs() {
        registerStub("/api/items/1", "get_item.json", "GET", 200);
        registerStub("/api/items", "create_item.json", "POST", 201);
        registerStub("/api/items/2", "update_item.json", "PUT", 200);
        registerStub("/api/items/2", "delete_item.json", "DELETE", 204);
    }

    private static void registerStub(String url, String jsonFile, String method, int statusCode) {
        try {
            String responseBody = new String(Files.readAllBytes(Paths.get("src/main/resources/stubs/" + jsonFile)));

            switch (method.toUpperCase()) {
                case "GET":
                    wireMockServer.stubFor(get(urlEqualTo(url))
                            .willReturn(aResponse()
                                    .withStatus(statusCode)
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(responseBody)));
                    break;
                case "POST":
                    wireMockServer.stubFor(post(urlEqualTo(url))
                            .withHeader("Content-Type", containing("application/json"))
                            .withRequestBody(containing("\"name\":"))
                            .willReturn(aResponse()
                                    .withStatus(statusCode)
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(responseBody)));
                    break;
                case "PUT":
                    wireMockServer.stubFor(put(urlEqualTo(url))
                            .withHeader("Content-Type", containing("application/json"))
                            .withRequestBody(containing("\"name\":"))
                            .willReturn(aResponse()
                                    .withStatus(statusCode)
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(responseBody)));
                    break;
                case "DELETE":
                    wireMockServer.stubFor(delete(urlEqualTo(url))
                            .willReturn(aResponse().withStatus(statusCode)));
                    break;
                default:
                    System.err.println("Invalid HTTP method: " + method);
            }
        } catch (IOException e) {
            System.err.println("Error reading stub file: " + jsonFile);
            e.printStackTrace();
        }
    }
}
