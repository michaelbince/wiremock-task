package utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockManager {
    private static final String STUBS_PATH = "src/main/resources/stubs/";
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
        registerGetStub();
        registerPostStub();
        registerPutStub();
        registerDeleteStub();
    }

    private static void registerGetStub() {
        wireMockServer.stubFor(get(urlPathEqualTo("/api/items/1"))
                .withQueryParam("type", equalTo("electronics"))
                .withHeader("Authorization", equalTo("Bearer token123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Cache-Control", "no-cache")
                        .withBody(loadResponseBody("get_item.json"))));
    }

    private static void registerPostStub() {
        wireMockServer.stubFor(post(urlEqualTo("/api/items"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(containing("\"name\":"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(loadResponseBody("create_item.json"))));
    }

    private static void registerPutStub() {
        wireMockServer.stubFor(put(urlPathMatching("/api/items/\\d+"))
                .withHeader("Authorization", equalTo("Bearer token123"))
                .withHeader("Content-Type", containing("application/json"))
                .withHeader("Accept", containing("application/json"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("Updated Item")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Cache-Control", "no-cache")
                        .withBody(loadResponseBody("update_item.json"))));
    }



    private static void registerDeleteStub() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/items/2"))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                        .withBody(loadResponseBody("delete_item.json"))));
    }

    private static String loadResponseBody(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(STUBS_PATH + fileName)));
        } catch (IOException e) {
            System.err.println("Error reading stub response file: " + fileName);
            e.printStackTrace();
            return "{}";
        }
    }
}
