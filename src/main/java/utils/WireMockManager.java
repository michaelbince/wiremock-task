package utils;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockManager {
    private static WireMockServer wireMockServer;

    public static void startWireMock() {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer(options().port(8080));
            wireMockServer.start();
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
        wireMockServer.stubFor(get(urlEqualTo("/api/items/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1, \"name\": \"Test Item\"}")));
    }

    private static void registerPostStub() {
        wireMockServer.stubFor(post(urlEqualTo("/api/items"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(containing("\"name\":"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 2, \"name\": \"New Item\"}")));
    }

    private static void registerPutStub() {
        wireMockServer.stubFor(put(urlEqualTo("/api/items/2"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(containing("\"name\":"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 2, \"name\": \"Updated Item\"}")));
    }

    private static void registerDeleteStub() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/items/2"))
                .willReturn(aResponse()
                        .withStatus(204)));
    }
}
