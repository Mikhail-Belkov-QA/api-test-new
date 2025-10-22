package api.assertions.conditions.tokenConditions;

import api.assertions.Condition;
import api.models.Token;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public class TokenCondition implements Condition {

    private final String token;

    @Override
    public void check(ValidatableResponse validatableResponse) {
        val actualToken = validatableResponse.extract().as(Token.class).getToken();
        step("Проверка токена", () ->
            assertThat(token)
                    .withFailMessage("Ожидалось, что токен будет %s, а он %s",
                            token, actualToken)
                    .isEqualTo(actualToken));
    }
}
