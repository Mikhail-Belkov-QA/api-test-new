package api.assertions;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import api.models.Token;

import java.util.List;

@RequiredArgsConstructor
public class AssertableResponse {

    private final ValidatableResponse response;

    public AssertableResponse should(Condition condition){
        condition.check(response);
        return this;
    }

    public String asToken(){
        // 1 вариант (строкой)
//        return response.extract().jsonPath().getString("token");
        // 2 вариант (через модель)
        return response.extract().as(Token.class).getToken();
    }

    public <T> T as(Class<T> tClass){
        return response.extract().as(tClass);
    }

    public <T> T as(String jsonPath, Class<T> tClass){
        return response.extract().jsonPath().getObject(jsonPath, tClass);
    }

    public <T> List<T> asList(Class<T> tClass){
        return response.extract().jsonPath().getList("", tClass);
    }

    public <T> List<T> asList(String jsonPath, Class<T> tClass){
        return response.extract().jsonPath().getList(jsonPath, tClass);
    }

    public Response asResponse(){
        return response.extract().response();
    }
}
