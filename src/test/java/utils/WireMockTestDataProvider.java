package utils;

import api.models.CreateItemRequest;
import api.models.UpdateItemRequest;
import org.testng.annotations.DataProvider;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unchecked")
public class WireMockTestDataProvider {

    private static final String TEST_DATA_FILE = "src/test/resources/testdata/wiremock_test_data.json";
    private static final Map<String, Object> testData = TestDataLoader.loadTestData(TEST_DATA_FILE);
    private static final String CREATE_ITEM = "createItemData";
    private static final String GET_ITEM = "getItemData";
    private static final String UPDATE_ITEM = "updateItemData";
    private static final String DELETE_ITEM = "deleteItemData";

    @DataProvider(name = "createItemTestData")
    public static Object[][] provideCreateItemTestData() {
        // Assuming createItemData is a list of maps
        ArrayList<Map<String, Object>> createItemData = (ArrayList<Map<String, Object>>) testData.get(CREATE_ITEM);

        Map<String, Object> item = createItemData.get(0); // Get the first map in the list

        CreateItemRequest requestBody = new CreateItemRequest();
        requestBody.setName(item.get("requestBodyName").toString());

        return new Object[][]{
                {
                        requestBody,
                        Integer.parseInt(item.get("expectedStatusCode").toString()),
                        item.get("expectedName").toString()
                }
        };
    }

    @DataProvider(name = "getItemTestData")
    public static Object[][] provideGetItemTestData() {
        // Assuming getItemData is a list of maps
        ArrayList<Map<String, Object>> getItemData = (ArrayList<Map<String, Object>>) testData.get(GET_ITEM);

        Map<String, Object> item = getItemData.get(0); // Get the first map in the list

        return new Object[][]{
                {
                        Integer.parseInt(item.get("itemId").toString()),
                        Integer.parseInt(item.get("expectedStatusCode").toString())
                }
        };
    }

    @DataProvider(name = "updateItemTestData")
    public static Object[][] provideUpdateItemTestData() {
        // Assuming updateItemData is a list of maps
        ArrayList<Map<String, Object>> updateItemData = (ArrayList<Map<String, Object>>) testData.get(UPDATE_ITEM);

        Map<String, Object> item = updateItemData.get(0); // Get the first map in the list

        UpdateItemRequest requestBody = new UpdateItemRequest();
        requestBody.setName(item.get("requestBodyName").toString());

        return new Object[][]{
                {
                        Integer.parseInt(item.get("itemId").toString()),
                        requestBody,
                        Integer.parseInt(item.get("expectedStatusCode").toString()),
                        item.get("expectedName").toString()
                }
        };
    }

    @DataProvider(name = "deleteItemTestData")
    public static Object[][] provideDeleteItemTestData() {
        // Assuming deleteItemData is a list of maps
        ArrayList<Map<String, Object>> deleteItemData = (ArrayList<Map<String, Object>>) testData.get(DELETE_ITEM);

        Map<String, Object> item = deleteItemData.get(0); // Get the first map in the list

        return new Object[][]{
                {
                        Integer.parseInt(item.get("itemId").toString()),
                        Integer.parseInt(item.get("expectedStatusCode").toString())
                }
        };
    }
}
