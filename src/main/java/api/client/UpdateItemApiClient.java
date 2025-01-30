package api.client;

import api.models.UpdateItemRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UpdateItemApiClient {
    private static final String UPDATE_ITEM_ENDPOINT = "/api/items/{id}";
    private static final String AUTH_TOKEN = "Bearer token123";

    public Response updateItem(int id, UpdateItemRequest requestBody) {
        return RestAssured.given()
                .header("Authorization", AUTH_TOKEN)
                .contentType("application/json")
                .accept("application/json")
                .pathParam("id", id)
                .body(requestBody)
                .put(UPDATE_ITEM_ENDPOINT);
    }
}
