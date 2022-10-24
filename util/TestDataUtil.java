package in.at.util;

import com.github.javafaker.Faker;

import java.util.Locale;

public class TestDataUtil {

    static Faker faker = new Faker(new Locale("en-IND"));

    public static String firstName() {
        return faker.name().firstName();
    }

    public static String lastName() {
        return faker.name().lastName();
    }

    public static String username() {
        return faker.name().username();
    }

    public static String password() {
        return faker.internet().password();
    }

    public static String phoneNumber() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    public static String address() {
        return faker.address().city();
    }

    public static String symptom() {
        return faker.medical().symptoms();
    }
}
