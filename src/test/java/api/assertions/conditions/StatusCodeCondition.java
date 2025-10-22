package api.assertions.conditions;

import api.assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {
    private final Integer statusCode;

    @Override
    public void check(ValidatableResponse validatableResponse) {
        val actualStatusCode = validatableResponse.extract().statusCode();

        step("Проверка статус кода", () -> {
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualStatusCode).withFailMessage(
                        "Ожидался статус код %s, а получен %s", statusCode, actualStatusCode
                ).isEqualTo(statusCode);
            });
        });
    }
}
