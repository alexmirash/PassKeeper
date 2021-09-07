package com.mirash.passkeeper.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mirash
 */
public class DbUtils {
    public static final String LOG_TAG = "DbC";

    public static List<Credentials> getTestPredefinedCredentials() {
        List<Credentials> list = new ArrayList<>();
        Random r = new Random();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            Credentials credentials = new Credentials();
            credentials.setTitle("Test_title_" + i);
            credentials.setLogin("test_login_" + i);
            if (r.nextBoolean()) {
                credentials.setLink("test_link_" + i);
            }
            if (r.nextBoolean()) {
                credentials.setDetails("test_details_" + i);
            }
            float value = r.nextFloat();
            if (value > 0.7) {
                credentials.setPassword("test_password_" + i);
                credentials.setPin("text_pin" + i);
            } else {
                if (value > 0.35) {
                    credentials.setPassword("test_password_" + i);
                } else {
                    credentials.setPin("text_pin" + i);
                }
            }
            list.add(credentials);
        }
        return list;
    }
}
