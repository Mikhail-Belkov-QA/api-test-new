package api.service;

import api.assertions.AssertableResponse;
import io.restassured.http.ContentType;
import api.models.FullUser;
import api.models.JwtAuthData;

import static api.helper.RequestHelper.getDefaultRequestSpec;

// захардкожена спецификация getDefaultRequestSpec()
public class UserService {

    public AssertableResponse register(FullUser user) {
        return new AssertableResponse(getDefaultRequestSpec()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/api/signup")
                .then());
    }

    public AssertableResponse auth(FullUser user) {
        JwtAuthData jwtAuthData = new JwtAuthData(user.getLogin(), user.getPass());
        return new AssertableResponse(getDefaultRequestSpec()
                .contentType(ContentType.JSON)
                .body(jwtAuthData)
                .post("/api/login")
                .then());
    }

    public AssertableResponse getUserInfo(String token){
        return new AssertableResponse(getDefaultRequestSpec()
                .auth().oauth2(token)
                .get("/api/user")
                .then());
    }
}

