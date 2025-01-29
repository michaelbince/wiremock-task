package tests.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.WireMockManager;

abstract public class APIBaseTest {

    @BeforeSuite
    public void beforeSuite() {
        WireMockManager.startWireMock();
    }

    @AfterSuite
    public void afterSuite() {
        WireMockManager.stopWireMock();
    }

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "http://localhost:8080";
    }

}
