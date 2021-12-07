import Commands.AddCategory;
import Commands.CheckTimeSpent;
import Commands.DecreaseBudget;
import Commands.SetBudget;
import Objects.Category;
import Objects.User;
import WorkingClasses.ActionsHandler;
import WorkingClasses.EntityManager;
import WorkingClasses.ServiceFunctions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceneTests {
    public ActionsHandler actionsHandler;
    public User user;
    public String userId;
    public static float budget;

    @BeforeEach
    void setUp() {
        actionsHandler = new ActionsHandler();
        userId = "42";
//        user = new User(userId);
        actionsHandler.processUserMessage(userId, "Узнать бюджет");
        budget = 1000;
    }

    @AfterEach
    void afterTest(){
        EntityManager.deleteUser(actionsHandler.getUsers().get(userId));
    }

    @Test
    void testCheckTwoUsers() {
        actionsHandler.processUserMessage("1", "Установить бюджет");
        actionsHandler.processUserMessage("2", "Установить бюджет");
        actionsHandler.processUserMessage("2", "10000");
        actionsHandler.processUserMessage("1", "15000");
        assertEquals(10000, actionsHandler.getUsers().get("2").checkMonthBudget());
        assertEquals(15000, actionsHandler.getUsers().get("1").checkMonthBudget());
    }

    @Test
    void testCheckFunctionality() {
        actionsHandler.processUserMessage(userId, "Установить бюджет");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        assertEquals(budget, actionsHandler.getUsers().get(userId).checkMonthBudget());
        float increase = 5000;
        actionsHandler.processUserMessage(userId, "Увеличить бюджет");
        actionsHandler.processUserMessage(userId, String.valueOf(increase));
        actionsHandler.processUserMessage(userId, "Ввести расходы");
        actionsHandler.processUserMessage(userId, "Продукты");
        actionsHandler.processUserMessage(userId, "1000");
        assertEquals(1000, actionsHandler.getUsers().get(userId).getCategories()
                .stream()
                .filter(x -> x.name.equals("Продукты")).
                findFirst().get().getAmountSpent());
        actionsHandler.processUserMessage(userId, "Добавить категорию расходов");
        actionsHandler.processUserMessage(userId, "Прочее");
        assertTrue(actionsHandler.getUsers().get(userId).getCategories().stream().anyMatch(x -> x.name.equals("Прочее")));
        actionsHandler.processUserMessage(userId, "Ввести расходы");
        actionsHandler.processUserMessage(userId, "Прочее");
        actionsHandler.processUserMessage(userId, "500");
        assertEquals(4500.0, actionsHandler.getUsers().get(userId).checkMonthBudget());
//        user.resetMonthBudget(20000f);
        actionsHandler.processUserMessage(userId, "Переустановить бюджет");
        actionsHandler.processUserMessage(userId, "20000");
        assertEquals(20000f, actionsHandler.getUsers().get(userId).checkMonthBudget());
    }

    @Test
    void testReadFile(){
        ServiceFunctions serviceFunctions = new ServiceFunctions();
        assertNotNull(ServiceFunctions.readFileContent("start.txt"));
        assertNull(ServiceFunctions.readFileContent("file.txt"));
    }

    @Test
    void testGetCommand(){
        Integer count = actionsHandler.getCommand().size();
        assertEquals(9, count);
    }
}
