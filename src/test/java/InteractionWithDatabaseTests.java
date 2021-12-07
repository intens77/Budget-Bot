import Objects.Category;
import Objects.User;
import WorkingClasses.ActionsHandler;
import WorkingClasses.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InteractionWithDatabaseTests {
    public ActionsHandler actionsHandler;
    public User user;

    @BeforeEach
    void setUp() {
        actionsHandler = new ActionsHandler();
        user = new User("777");
    }

    @AfterEach
    void deleteTestUser() {
        EntityManager.deleteUser(user);
        EntityManager.deleteUser(new User("1001"));
    }

    @Test
    void testSaveNonExistingUser() {
        User currentUser = new User();
        currentUser.setCategories(new ArrayList<>());
        currentUser.setTelegramId("1001");
        EntityManager.saveUser(currentUser);
        assertNotNull(EntityManager.findUserByTelegramId("1001"));
        EntityManager.deleteUser(currentUser);
    }

    @Test
    void testSaveExistingUser() {
        EntityManager.saveUser(user);
        user = EntityManager.findUserByTelegramId(user.getTelegramId());
        assertNotNull(user);
    }

//    @Test
//    void testDeleteNonExistingUser() {
//        assertThrows(Throwable.class, EntityManager.deleteUser(new User("1001")));
//    }

    @Test
    void testUpdateUser() {
        float sum = 15000;
        float newUserBudget = user.getMonthBudget() + sum;
        user.increaseMonthBudget(sum);
        assertEquals(newUserBudget, user.getMonthBudget());
        Category category = new Category("спорт", 1000);
        user.addCategory(category);
        assertTrue(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(category.name));
    }

    @Test
    void testMonthBudget() {
        user.setMonthBudget(10000);
        User newUser = EntityManager.findUserByTelegramId(user.getTelegramId());
        assertEquals(user.checkMonthBudget(), newUser.checkMonthBudget());
    }

    @Test
    void testContainsCategory() {
        Category category = new Category("Категория 1", 0);
        user.addCategory(category);
        assertTrue(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(category.name));
    }

    @Test
    void categoriesChangingSceneTest() {
        Category sport = new Category("спорт", 3000);
        Category health = new Category("здоровье", 2500);
        Category food = new Category("еда", 6500);
        user.addCategory(sport);
        assertTrue(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(sport.name));
        user.removeCategory(sport);
        assertFalse(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(sport.name));
        user.addCategory(health);
        user.addCategory(food);
        assertTrue(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(food.name));
        assertTrue(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(health.name));
        user.removeCategory(health);
        assertFalse(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(health.name));
        user.removeCategory(food);
        assertFalse(EntityManager.findUserByTelegramId(user.getTelegramId()).containsCategory(food.name));
    }

    @Test
    void budgetChangingSceneTest() {
        user.setMonthBudget(30000);
        user.increaseMonthBudget(20000);
        user.decreaseMonthBudget(5000);
        assertEquals(user.checkMonthBudget(), EntityManager.findUserByTelegramId(user.getTelegramId()).checkMonthBudget());
        user.setMonthBudget(100000);
        user.decreaseMonthBudget(50000);
        user.increaseMonthBudget(10000);
        assertEquals(user.checkMonthBudget(), EntityManager.findUserByTelegramId(user.getTelegramId()).checkMonthBudget());
        user.resetMonthBudget(77);
        assertEquals(user.checkMonthBudget(), EntityManager.findUserByTelegramId(user.getTelegramId()).checkMonthBudget());
    }
}
