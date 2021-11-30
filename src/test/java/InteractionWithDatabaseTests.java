import Objects.User;
import WorkingClasses.EntityManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InteractionWithDatabaseTests {

    @Test
    void testUserWasFound() {
        int id = 5;
        User user = EntityManager.findUserById(id);
        assertEquals(id, user.getId());
    }

    @Test
    void testUserWasNotFound() {
        int id = -1;
        User user = EntityManager.findUserById(id);
        assertNull(user);
    }
}
