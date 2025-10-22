package api.helper;

import config.AppConfig;
import config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class RequestHelper {

    public static RequestSpecification getDefaultRequestSpec() {
        return given()
                .contentType(JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured())
                .log().all()
                .baseUri(Config.getInstance().baseUrl());
    }
}
