package api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteItemApiClient {
    private static final String DELETE_ITEM_ENDPOINT = "/api/items/{id}";

    public Response deleteItem(int id) {
        return RestAssured.given()
                .delete(DELETE_ITEM_ENDPOINT.replace("{id}", String.valueOf(id)));
    }
}
