package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    //private static void sendRequest(RegistrationDto user) {
    //    @Data
    //    @RequiredArgsConstructor
    //    public class UserInfo {
    //        private final String login;
    //        private final String password;
    //        private final String status;
    //    }

    //}
private static RegistrationDto sendRequest(RegistrationDto user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new RegistrationDto("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200);
        return user;
    }

    public static String getRandomLogin() {
        Faker faker = new Faker();

        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        Faker faker = new Faker();

        return FAKER.internet().password();
    }
   // public static String generateStatus(boolean status) {
   //     if (status) {
   //         return "active";
   //     } else {
   //         return "blocked";
   //     }
   // }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {

            return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        }

        public static RegistrationDto getRegisteredUser(String status) {

            return sendRequest(getUser(status));
        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}