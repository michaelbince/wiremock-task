package api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetItemApiClient {
    private static final String GET_ITEM_ENDPOINT = "/api/items/{id}";

    public Response getItem(int id) {
        return RestAssured.given()
                .get(GET_ITEM_ENDPOINT.replace("{id}", String.valueOf(id)));
    }
}
