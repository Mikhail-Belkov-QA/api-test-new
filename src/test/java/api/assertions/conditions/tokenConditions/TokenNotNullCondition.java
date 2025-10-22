package api.assertions.conditions.tokenConditions;

import api.assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.val;
import api.models.Token;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TokenNotNullCondition implements Condition {

    @Override
    public void check(ValidatableResponse validatableResponse) {
        val token = validatableResponse.extract().as(Token.class);
        step("Проверка токена", () ->
            assertThat(token.getToken())
                    .withFailMessage("Ожидалось, что токен будет не пустой")
                    .isNotNull()
        );
    }
}
