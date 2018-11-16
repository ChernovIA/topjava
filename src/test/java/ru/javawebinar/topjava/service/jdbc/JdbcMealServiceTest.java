package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import java.util.stream.Stream;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Autowired
    private Environment environment;

    public void checkProfile() {
        Assume.assumeTrue(isJdbsProfile());
    }

    private boolean isJdbsProfile() {
        return Stream.of(environment.getActiveProfiles()).anyMatch(s-> s.equals("jdbс"));
    }

    @Override
    public void testValidation() throws Exception {
        checkProfile();
    }
}