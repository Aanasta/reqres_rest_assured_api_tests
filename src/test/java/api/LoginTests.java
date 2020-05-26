package api;

import entities.Login;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static testdata.Endpoints.BASE_ENDPOINT;
import static testdata.Endpoints.LOGIN_ENDPOINT;
import static testdata.TestdataLogin.*;

@Feature("Login")
public class LoginTests {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Verify an existing user with valid credentials is logged in via API")
    public void validUserIsLoggedIn(){
        Login credentials = new Login(VALID_LOGIN_EMAIL, VALID_LOGIN_PASSWORD);

        given().
                header("Content-Type", "application/json").
                body(credentials).
        when().
                post(BASE_ENDPOINT + LOGIN_ENDPOINT).
        then().
                assertThat().statusCode(200).
                assertThat().body("token", notNullValue());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Verify a non-existing user is not logged in via API with expected error")
    public void invalidUserIsNotLoggedIn(){
        Login credentials = new Login(INVALID_LOGIN_EMAIL, VALID_LOGIN_PASSWORD);

        given().
                header("Content-Type", "application/json").
                body(credentials).
        when().
                post(BASE_ENDPOINT + LOGIN_ENDPOINT).
        then().
                assertThat().statusCode(400).
                assertThat().body("error", hasToString(EXPECTED_INVALID_USER_MESSAGE));
    }
}
