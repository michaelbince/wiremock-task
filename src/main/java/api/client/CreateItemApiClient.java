package api.client;

import api.models.CreateItemRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateItemApiClient {
    private static final String CREATE_ITEM_ENDPOINT = "/api/items";

    public Response createItem(CreateItemRequest requestBody) {
        return RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(CREATE_ITEM_ENDPOINT);
    }
}
