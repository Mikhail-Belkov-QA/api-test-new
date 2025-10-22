package api.assertions.conditions;

import api.assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import api.models.Info;
import org.junit.jupiter.api.Assertions;

@RequiredArgsConstructor
public class MessageCondition implements Condition {
    private final String message;

    @Override
    public void check(ValidatableResponse validatableResponse) {
        Info info = validatableResponse.extract().jsonPath().getObject("info", Info.class);
        Assertions.assertEquals(message, info.getMessage());
    }
}
