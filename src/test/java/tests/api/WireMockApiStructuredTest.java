package tests.api;

import api.client.CreateItemApiClient;
import api.client.DeleteItemApiClient;
import api.client.GetItemApiClient;
import api.client.UpdateItemApiClient;
import api.models.CreateItemRequest;
import api.models.UpdateItemRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.WireMockTestDataProvider;

public class WireMockApiStructuredTest extends APIBaseTest {

    private CreateItemApiClient createItemApiClient;
    private GetItemApiClient getItemApiClient;
    private UpdateItemApiClient updateItemApiClient;
    private DeleteItemApiClient deleteItemApiClient;

    @BeforeMethod
    public void setUp() {
        createItemApiClient = new CreateItemApiClient();
        getItemApiClient = new GetItemApiClient();
        updateItemApiClient = new UpdateItemApiClient();
        deleteItemApiClient = new DeleteItemApiClient();
    }

    @Test(description = "Create a new item and verify the HTTP status code and name.",
            dataProvider = "createItemTestData",
            dataProviderClass = WireMockTestDataProvider.class)
    public void testCreateItem(CreateItemRequest requestBody, int expectedStatusCode, String expectedName) {
        Response response = createItemApiClient.createItem(requestBody);
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Expected HTTP status code " + expectedStatusCode);
        Assert.assertEquals(response.jsonPath().getString("name"), expectedName, "Expected item name does not match.");
    }

    @Test(description = "Get an item and verify the HTTP status code.",
            dataProvider = "getItemTestData",
            dataProviderClass = WireMockTestDataProvider.class)
    public void testGetItem(int itemId, int expectedStatusCode) {
        Response response = getItemApiClient.getItem(itemId);
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Expected HTTP status code " + expectedStatusCode);
    }

    @Test(description = "Update an item and verify the HTTP status code and updated name.",
            dataProvider = "updateItemTestData",
            dataProviderClass = WireMockTestDataProvider.class)
    public void testUpdateItem(int itemId, UpdateItemRequest requestBody, int expectedStatusCode, String expectedName) {
        Response response = updateItemApiClient.updateItem(itemId, requestBody);
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Expected HTTP status code " + expectedStatusCode);
        Assert.assertEquals(response.jsonPath().getString("name"), expectedName, "Expected updated item name does not match.");
    }

    @Test(description = "Delete an item and verify the HTTP status code.",
            dataProvider = "deleteItemTestData",
            dataProviderClass = WireMockTestDataProvider.class)
    public void testDeleteItem(int itemId, int expectedStatusCode) {
        Response response = deleteItemApiClient.deleteItem(itemId);
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Expected HTTP status code " + expectedStatusCode);
    }
}
