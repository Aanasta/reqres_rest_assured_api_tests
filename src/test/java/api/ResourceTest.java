package api;

import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static testdata.Endpoints.*;
import static testdata.TestdataResource.EXPECTED_RESOURCE_COLOR;
import static testdata.TestdataResource.EXPECTED_RESOURCE_NAME;

@Feature("Resource")
public class ResourceTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Verify Resource list is available via API")
    public void resourceListIsAvailable(){
        when().
                get(BASE_ENDPOINT + RESOURCE_ENDPOINT).
        then().
                assertThat().statusCode(200).
                assertThat().body("data.id", hasItems(1, 2, 3, 4, 5, 6)).
                assertThat().body("data.name", hasItem(EXPECTED_RESOURCE_NAME));
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Verify a Resource is available by id via API")
    public void existingResourceIsAvailableById(){
        when().
                get(BASE_ENDPOINT + RESOURCE_ENDPOINT + VALID_ENDPOINT).
        then().
                assertThat().statusCode(200).
                assertThat().body("data.color", equalTo(EXPECTED_RESOURCE_COLOR)).
                assertThat().body("data.name", equalTo(EXPECTED_RESOURCE_NAME));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Verify a non-existing Resource is not available by id via API with ")
    public void nonExistingResourceIsNotAvailableById(){
        when().
                get(BASE_ENDPOINT + USERS_ENDPOINT + INVALID_ENDPOINT).
        then().
                assertThat().statusCode(404).
                assertThat().body("isEmpty()", Matchers.is(true));
    }
}
