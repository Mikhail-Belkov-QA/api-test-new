package api.service;

import api.assertions.AssertableResponse;
import api.models.FullUser;
import api.models.JwtAuthData;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;


// Спецификация не захордкожена, благодаря AbstractBaseService
public class UserServiceMisha extends AbstractBaseService<UserServiceMisha> {

    protected UserServiceMisha(RequestSpecification requestSpecification) {
        super(requestSpecification);
    }

    public AssertableResponse register(FullUser user) {
        return new AssertableResponse(getRequestSpecification()
                .body(user)
                .post(getApi() + "/signup")
                .then());
    }

    public AssertableResponse auth(FullUser user) {
        JwtAuthData jwtAuthData = new JwtAuthData(user.getLogin(), user.getPass());
        return new AssertableResponse(getRequestSpecification()
                .body(jwtAuthData)
                .post(getApi() + "/login")
                .then());
    }

    public AssertableResponse getUserInfo(String token) {
        return new AssertableResponse(getRequestSpecification()
                .auth().oauth2(token)
                .get(getApi() + "/user")
                .then());
    }

    public AssertableResponse getUserInfo() {
        return new AssertableResponse(getRequestSpecification()
                .get(getApi() + "/user")
                .then());
    }

    public AssertableResponse updatePass(String token, String newPassword) {
        HashMap<String, String> password = new HashMap<>();
        password.put("password", newPassword);

        return new AssertableResponse(getRequestSpecification()
                .auth().oauth2(token)
                .body(password)
                .put(getApi() + "/user")
                .then());
    }
}

