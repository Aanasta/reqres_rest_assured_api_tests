package api;

import entities.User;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static testdata.Endpoints.*;
import static testdata.TestdataUser.*;

@Feature("User")
public class UserTests {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Verify User list is available via API")
    public void usersListIsAvailable(){
        when().
                get(BASE_ENDPOINT + USERS_ENDPOINT).
        then().
                assertThat().statusCode(200).
                assertThat().body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Verify an existing User is available by id via API")
    public void existingUserIsAvailableById(){
        when().
                get(BASE_ENDPOINT + USERS_ENDPOINT + VALID_ENDPOINT).
        then().
                assertThat().statusCode(200).
                assertThat().body("data.first_name", equalTo(EXPECTED_USER_NAME)).
                assertThat().body("data.email", equalTo(EXPECTED_USER_EMAIL));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Verify a non-existing User is not available by id via API with expected error")
    public void nonExistingUserIsNotAvailableById(){
        when().
                get(BASE_ENDPOINT + USERS_ENDPOINT + INVALID_ENDPOINT).
        then().
                assertThat().statusCode(404).
                assertThat().body("isEmpty()", Matchers.is(true));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify a new user is created via API")
    public void newUserIsCreated(){
        User user = new entities.User(NEW_USER_NAME, NEW_USER_JOB);

        given().
                header("Content-Type", "application/json").
                body(user).
        when().
                post(BASE_ENDPOINT + USERS_ENDPOINT).
        then().
                assertThat().statusCode(201).
                assertThat().body("name", equalTo(NEW_USER_NAME)).
                assertThat().body("job", equalTo(NEW_USER_JOB));        ;
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify an existing user is updated via API")
    public void existingUserIsUpdated(){
        User user = new entities.User(UPDATED_USER_NAME, UPDATED_USER_JOB);

        given().
                header("Content-Type", "application/json").
                body(user).
        when().
                patch(BASE_ENDPOINT + USERS_ENDPOINT + VALID_ENDPOINT).
        then().
                assertThat().statusCode(200).
                assertThat().body("name", equalTo(UPDATED_USER_NAME)).
                assertThat().body("job", equalTo(UPDATED_USER_JOB));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Verify an existing user is deleted via API")
    public void existingUserIsDeleted(){
        when().
                delete(BASE_ENDPOINT + USERS_ENDPOINT + VALID_ENDPOINT).
        then().
                assertThat().statusCode(204).
                assertThat().body(emptyString());
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Verify User list is available via API after a timeout")
    public void userListIsAvailableWithTimeout() {
        RestAssured.config= RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig().
                setParam("http.connection-manager.timeout",3500));

        when().
                get(BASE_ENDPOINT + USERS_ENDPOINT + TIMEOUT_PARAMETER).
        then().
                assertThat().statusCode(200).
                assertThat().body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }
}
