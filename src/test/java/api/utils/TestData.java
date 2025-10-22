package api.utils;

import com.github.javafaker.Faker;
import api.models.FullUser;

import java.util.Random;

public class TestData {

    private static Random random = new Random();
    private static Faker faker = new Faker();

    public static FullUser getRandomUser(){
        return FullUser.builder()
                .login(faker.name().firstName() + " " + Math.abs(random.nextInt()))
                .pass("12345")
                .build();
    }

    public static FullUser getAdmin(){
        return FullUser.builder()
                .login("admin")
                .pass("admin")
                .build();
    }

   public static String generateRandomString(){
       int length = faker.number().numberBetween(1, 50);
       return faker.lorem().characters(length);
   }
}
