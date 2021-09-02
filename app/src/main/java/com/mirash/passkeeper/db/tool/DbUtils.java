package com.mirash.passkeeper.db.tool;

import com.mirash.passkeeper.db.Credentials;

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
        int count = 10;
        for (int i = 0; i < count; i++) {
            Credentials credentials = new Credentials();
            credentials.setTitle("Test_title_" + i);
            credentials.setLogin("test_login_" + i);
            credentials.setPassword("test_password_" + i);
            if (r.nextBoolean()) {
                credentials.setLink("test_link_" + i);
            }
            if (r.nextBoolean()) {
                credentials.setDetails("test_details_" + i);
            }
            list.add(credentials);
        }
        return list;
    }
}
