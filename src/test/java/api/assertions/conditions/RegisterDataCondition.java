package api.assertions.conditions;

import api.assertions.Condition;
import io.restassured.response.ValidatableResponse;
import lombok.val;
import api.models.RegisterData;
import org.assertj.core.api.SoftAssertions;

import java.util.function.Consumer;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class RegisterDataCondition implements Condition {

    @Override
    public void check(ValidatableResponse validatableResponse) {
        val registerData = validatableResponse.extract().as(RegisterData.class);
        step(
                "Проверка наличия каких-либо значений в обязательных полях ответа",
                () -> assertSoftly(softAssertionRegisterData(registerData))
        );
    }

    // стоит тип Consumer<SoftAssertions>, тк именно его принимает assertSoftly()
    private static Consumer<SoftAssertions> softAssertionRegisterData(RegisterData registerData) {
        return softAssertions -> {
            softAssertions.assertThat(registerData.getPass())
                    .withFailMessage("Ожидалось, что pass будет не пустой")
                    .isNotNull();
            softAssertions.assertThat(registerData.getGames())
                    .withFailMessage("Ожидалось, что games будут не пустые")
                    .isNotNull();
            softAssertions.assertThat(registerData.getId())
                    .withFailMessage("Ожидалось, что id будет не пустой")
                    .isNotNull();
            softAssertions.assertThat(registerData.getLogin())
                    .withFailMessage("Ожидалось, что login будет не пустой")
                    .isNotNull();
        };
    }
}
