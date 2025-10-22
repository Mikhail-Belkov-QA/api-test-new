package api.extensions;

import api.models.FullUser;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Random;

public class RandomUserResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(RandomUser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if(FullUser.class.equals(type)){
            Random random = new Random();
            Faker faker = new Faker();
            return FullUser.builder()
                    .login(faker.name().firstName() + " " + Math.abs(random.nextInt()))
                    .pass("12345")
                    .build();
        }
        throw new ParameterResolutionException("Пользователь не блы сгенерирован");
    }
}
