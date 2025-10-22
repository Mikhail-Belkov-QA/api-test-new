package api.assertions;

import api.assertions.conditions.*;
import api.assertions.conditions.tokenConditions.TokenCondition;
import api.assertions.conditions.tokenConditions.TokenNotNullCondition;

public class Conditions {

    public static StatusCodeCondition hasStatusCode(Integer statusCode){
        return new StatusCodeCondition(statusCode);
    }

    public static MessageCondition hasMessage(String message){
        return new MessageCondition(message);
    }

    public static ErrorCondition hasError(String error){
        return new ErrorCondition((error));
    }

    public static UserInfoCondition hasInfo(String message, String status){
        return new UserInfoCondition(message, status);
    }
    public static RegisterDataCondition hasNoEmptyValues(){
        return new RegisterDataCondition();
    }

    public static TokenNotNullCondition hasNotEmptyToken(){
        return new TokenNotNullCondition();
    }

    public static TokenCondition hasToken(String token){
        return new TokenCondition(token);
    }
}
