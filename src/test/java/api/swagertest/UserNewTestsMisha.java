package api.swagertest;

import api.assertions.Conditions;
import api.extensions.AdminUserResolver;
import api.utils.TestData;
import api.utils.constants.ResponseErrors;
import api.utils.constants.ResponseMessages;
import api.extensions.AdminUser;
import api.extensions.RandomUser;
import api.extensions.RandomUserResolver;
import lombok.val;
import api.models.FullUser;
import org.awaitility.core.ConditionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static api.helper.RequestHelper.getDefaultRequestSpec;
import static api.service.ServiceManager.*;
import static java.util.concurrent.TimeUnit.*;
import static org.awaitility.Awaitility.await;

@ExtendWith({
        AdminUserResolver.class,
        RandomUserResolver.class
})
@Tag("MishaTests")
public class UserNewTestsMisha {

    //  Влипил эвэйтилити просто как пример, чтоб было
    private static final ConditionFactory WAIT = await()
            .atMost(60, SECONDS)
            .pollInterval(1, SECONDS);

    private FullUser user;

    @BeforeEach
    public void initTestUser() {
        user = TestData.getRandomUser();
    }

    @Test
    void positiveRegisterTest() {
        getUserServiceMisha().register(user)
                .should(Conditions.hasStatusCode(201))
                .should(Conditions.hasInfo(ResponseMessages.CREATED_USER_MESSAGE, ResponseMessages.SUCCESS_STATUS));
    }


    // getUserServiceMisha2 принимает на вход спеку прямо в тесте. Там так сделано, тк мы используем
    // захардкоженную спеку из библиотеки "сценаристов", а сам тестовый класс наследуется от абстрктного класса,
    // который эту спеку и описывает
    @Test
    void positiveRegisterTest2() {
        getUserServiceMisha2(getDefaultRequestSpec()).register(user)
                .should(Conditions.hasStatusCode(201))
                .should(Conditions.hasInfo(ResponseMessages.CREATED_USER_MESSAGE, ResponseMessages.SUCCESS_STATUS));
    }

    //  Влипил эвэйтилити просто как пример, чтоб было
    @Test
    void negativeRegisterLoginExistTest() {
        getUserServiceMisha().register(user);
        WAIT.untilAsserted(() -> {
            getUserServiceMisha().register(user)
                    .should(Conditions.hasInfo(ResponseMessages.LOGIN_EXIST_MESSAGE, ResponseMessages.FAIL_STATUS));
        });
    }

    @Test
    public void negativeRegisterNoPasswordTest() {
        user.setPass(null);
        getUserServiceMisha().register(user)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasInfo(ResponseMessages.MISSING_LOGIN_OR_PASSWORD_MESSAGE, ResponseMessages.FAIL_STATUS));
    }

    @Test
    public void positiveAdminAuthTest() {
        getUserServiceMisha().auth(TestData.getAdmin())
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasNotEmptyToken());
    }

    // То что и тест выше, только с экстеншеном для админа. Чисто для примера. Здесь нас не интересует beforeEach
    @Test
    public void positiveAdminAuthTest2(@AdminUser FullUser admin) {
        getUserServiceMisha().auth(admin)
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasNotEmptyToken());
    }

    @Test
    public void positiveNewUserAuthTest() {
        getUserServiceMisha().register(user);
        getUserServiceMisha().auth(user)
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasNotEmptyToken());
    }

    // То что и тест выше, только с экстеншеном для рандомного пользака. Чисто для примера. Здесь нас не интересует beforeEach
    @Test
    public void positiveNewUserAuthTest2(@RandomUser FullUser randomUser) {
        getUserServiceMisha().register(randomUser);
        getUserServiceMisha().auth(randomUser)
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasNotEmptyToken());
    }

    @Test
    public void negativeAuthTest() {
        getUserServiceMisha().auth(user)
                .should(Conditions.hasStatusCode(401))
                .should(Conditions.hasError(ResponseErrors.UNAUTHORIZED));
    }

    @Test
    public void positiveGetUserInfoTest() {
        val token = getUserServiceMisha().auth(TestData.getAdmin())
                .asToken();
        getUserServiceMisha().getUserInfo(token)
                .should(Conditions.hasStatusCode(200))
                //отсебятинская проверка
                .should(Conditions.hasNoEmptyValues());
    }

    @Test
    public void negativeGetUserInfoInvalidJwtTest() {
        getUserServiceMisha().getUserInfo(TestData.generateRandomString())
                .should(Conditions.hasStatusCode(401));
    }

    @Test
    public void negativeGetUserInfoWithoutJwtTest() {
        getUserServiceMisha().getUserInfo()
                .should(Conditions.hasStatusCode(401))
                .should(Conditions.hasError(ResponseErrors.UNAUTHORIZED));
    }

    @Test
    public void positiveChangeUserPassTest() {
        val oldPassword = user.getPass();
        val newPassword = "newPassword";

        getUserServiceMisha().register(user);
        var token = getUserServiceMisha()
                .auth(user)
                .asToken();

        getUserServiceMisha().updatePass(token, newPassword)
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasMessage(ResponseMessages.PASSWORD_UPDATE));

        user.setPass(newPassword);

        token = getUserServiceMisha().auth(user)
                .should(Conditions.hasStatusCode(200))
                .asToken();

        val updatedUser = getUserServiceMisha().getUserInfo(token).as(FullUser.class);

        Assertions.assertNotEquals(oldPassword, updatedUser.getPass());
    }

    // Проверка на невозможность изменить пароль админу
    @Test
    public void negativeChangeAdminPasswordTest() {
        val token = getUserServiceMisha().auth(TestData.getAdmin()).asToken();
        val updatedPass = "newPass";
        getUserServiceMisha().updatePass(token, updatedPass)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasInfo(ResponseMessages.PASSWORD_UPDATE_FAIL, ResponseMessages.FAIL_STATUS));
    }
}
