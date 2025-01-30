package api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetItemApiClient {
    private static final String GET_ITEM_ENDPOINT = "/api/items/{id}";
    private static final String AUTH_TOKEN = "Bearer token123";

    public Response getItem(int id, String queryParam) {
        RequestSpecification request = RestAssured.given()
                .header("Authorization", AUTH_TOKEN)
                .header("Accept", "application/json")
                .pathParam("id", id)
                .queryParam("type", queryParam);

        return request.get(GET_ITEM_ENDPOINT);
    }
}
