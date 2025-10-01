
import databasePart1.DatabaseHelper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import application.User;

public class DatabaseHelperTest {

    private static DatabaseHelper dbHelper;

    @BeforeAll
    static void setup() throws SQLException {
        dbHelper = new DatabaseHelper();
        dbHelper.connectToDatabase();
    }

    @AfterAll
    static void tearDown() {
        dbHelper.closeConnection();
    }

    @Test
    void testDatabaseStartsEmpty() throws SQLException {
        // Assuming database is cleared before testing
        boolean isEmpty = dbHelper.isDatabaseEmpty();
        assertTrue(isEmpty, "Database should start empty");
    }

    @Test
    void testRegisterUser() throws SQLException {
        User user = new User("testUser", "Password123!", "user");
        dbHelper.register(user);

        assertTrue(dbHelper.doesUserExist("testUser"), "User should exist after registration");
        assertEquals("user", dbHelper.getUserRole("testUser"), "User role should be 'user'");
    }

    @Test
    void testLoginSuccess() throws SQLException {
        User user = new User("loginUser", "Password123!", "user");
        dbHelper.register(user);

        assertTrue(dbHelper.login(user), "Login should succeed with correct credentials");
    }

    @Test
    void testLoginFail() throws SQLException {
        User user = new User("nonExistent", "nopass", "user");
        assertFalse(dbHelper.login(user), "Login should fail for non-existent user");
    }

    @Test
    void testDuplicateUserRegistration() throws SQLException {
        User user = new User("duplicateUser", "Password123!", "user");
        dbHelper.register(user);

        Exception exception = assertThrows(SQLException.class, () -> {
            dbHelper.register(user);
        });

        String expectedMessage = "unique"; // H2 throws an exception mentioning UNIQUE constraint
        String actualMessage = exception.getMessage().toLowerCase();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
