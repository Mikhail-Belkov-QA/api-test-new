package api.assertions.conditions;

import api.assertions.Condition;
import api.models.UserResponse;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.assertj.core.api.SoftAssertions;


import java.util.function.Consumer;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;


@RequiredArgsConstructor
public class UserInfoCondition implements Condition {

    private final String message;
    private final String status;

    // 1 вариант
//    @Override
//    public void check(ValidatableResponse validatableResponse) {
//        val userResponse = validatableResponse.extract().as(UserResponse.class);
//        step("Проверка блока info в ответе", () -> {
//            assertSoftly(softAssertions -> {
//                softAssertions.assertThat(userResponse.getInfo().getMessage())
//                        .withFailMessage("Ожидалось сообщение %s, а получили %s", message,
//                                userResponse.getInfo().getMessage())
//                        .isEqualTo(message);
//                softAssertions.assertThat(userResponse.getInfo().getStatus())
//                        .withFailMessage("Ожидался статус %s, а получили %s", status,
//                                userResponse.getInfo().getStatus())
//                        .isEqualTo(status);
//            });
//        });
//    }

    // 2 вариант. Лучше первого тем, что сами кишки проверок вынесены в отдельный метод softAssertionUserInfo().
    // Тогда в методе check останется только аллюровский стэр и сам ассерт
    @Override
    public void check(ValidatableResponse validatableResponse) {
        val userResponse = validatableResponse.extract().as(UserResponse.class);
        step("Проверка блока info в ответе", () -> {
            assertSoftly(softAssertionUserInfo(message, status, userResponse));
        });
    }

    // стоит тип Consumer<SoftAssertions>, тк именно его принимает assertSoftly()
    private static Consumer<SoftAssertions> softAssertionUserInfo(String message, String status, UserResponse userResponse) {
        return softAssertions -> {
            softAssertions.assertThat(userResponse.getInfo().getMessage())
                    .withFailMessage("Ожидалось сообщение %s, а получили %s", message,
                            userResponse.getInfo().getMessage())
                    .isEqualTo(message);
            softAssertions.assertThat(userResponse.getInfo().getStatus())
                    .withFailMessage("Ожидался статус %s, а получили %s", status,
                            userResponse.getInfo().getStatus())
                    .isEqualTo(status);
        };
    }
}
