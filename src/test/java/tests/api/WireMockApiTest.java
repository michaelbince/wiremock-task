package tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WireMockApiTest extends APIBaseTest {

    @Test
    public void testGetItem() {
        Response response = RestAssured.get("http://localhost:8080/api/items/1");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Test Item");
    }

    @Test
    public void testCreateItem() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \"New Item\"}")
                .post("http://localhost:8080/api/items");

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), "New Item");
    }

    @Test
    public void testUpdateItem() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \"Updated Item\"}")
                .put("http://localhost:8080/api/items/2");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Updated Item");
    }

    @Test
    public void testDeleteItem() {
        Response response = RestAssured.delete("http://localhost:8080/api/items/2");
        Assert.assertEquals(response.getStatusCode(), 204);
    }
}

