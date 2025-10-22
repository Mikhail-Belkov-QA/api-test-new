package api.assertions.conditions;

import api.assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import api.models.User401Response;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RequiredArgsConstructor
public class ErrorCondition implements Condition {

    private final String error;

    @Override
    public void check(ValidatableResponse validatableResponse) {
        val actualError = validatableResponse.extract().as(User401Response.class).getError();

        step("Проверка поля error", () -> {
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualError).withFailMessage(
                        "Ожидалась ошибка %s, а получена %s", actualError, error).isEqualTo(error);
            });
        });
    }
}
