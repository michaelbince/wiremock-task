package api.client;

import api.models.UpdateItemRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UpdateItemApiClient {
    private static final String UPDATE_ITEM_ENDPOINT = "/api/items/{id}";

    public Response updateItem(int id, UpdateItemRequest requestBody) {
        return RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .put(UPDATE_ITEM_ENDPOINT.replace("{id}", String.valueOf(id)));
    }
}
