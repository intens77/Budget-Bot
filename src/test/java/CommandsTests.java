import Commands.AddCategory;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandsTests {
    public static String parameterRequestMessage;
    public static String commandError;
    public static String parameterErrorMessage;
    public static ActionsHandler actionsHandler;
    public static float budget;
    public static String productsCosts;
    public static String cafeCosts;
    public User user;
    public String userId;

    @BeforeAll
    static void initBefore() {
        actionsHandler = new ActionsHandler();
        parameterRequestMessage = "Введите параметры:";
        parameterErrorMessage = ServiceFunctions.generateCommandParameterError();
        commandError = ServiceFunctions.generateCommandError();
        budget = 1000;
        productsCosts = "Продукты, 200";
        cafeCosts = "Кафе, 500";
    }

    @BeforeEach
    void setUp() {
        userId = "42";
        user = new User(userId);
    }

    @AfterEach
    void deleteTestUser() {
        EntityManager.deleteUser(user);
    }

    @Test
    void testStartProcess() {
        String result = ServiceFunctions.readFileContent("start.txt");
        assertEquals(result, actionsHandler.processUserMessage(userId, "старт"));
    }

    @Test
    void testGetStrategies() {
        String result = "Стратегия 1\n Стратегия 2\n Стратегия 3";
        assertEquals(result, actionsHandler.processUserMessage(userId, "Посмотреть стратегии"));
    }

    @Test
    void testNegativelySetBudget() {
        float budget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId, "Установить бюджет"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, String.valueOf(budget)));
    }

    @Test
    void testPositivelySetBudget() {
        float budget = 100;
        String resultMessage = String.format("Отлично, Вы установили ваш " + "ежемесячный бюджет. Он составляет %s рублей", budget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId, "Установить бюджет"));
        assertEquals(resultMessage, actionsHandler.processUserMessage(userId, String.valueOf(budget)));
    }

    @Test
    void testNegativelyIncreaseBudget() {
        float increase = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId, "Увеличить бюджет"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, String.valueOf(increase)));
    }

    @Test
    void testNegativelyDecreaseBudget() {
        String decrease = "Кафе";
        String sum = "-100";
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId, "Ввести расходы"));
        actionsHandler.processUserMessage(userId, decrease);
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, sum));
    }

    @Test
    void testPositivelyDecreasePositiveBudget() {
        String decrease = "Продукты";
        String sum = "100";
        actionsHandler.processUserMessage(userId, "Установить бюджет");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId, "Ввести расходы"));
        String expected = String.format("Отлично, Вы уменьшили ваш " + "ежемесячный бюджет. Он составляет %s рублей", budget - Float.parseFloat(sum));
        actionsHandler.processUserMessage(userId, decrease);
        String actual = actionsHandler.processUserMessage(userId, sum);
        assertEquals(expected, actual);
    }

    @Test
    void testNegativelyResetBudget() {
        float newBudget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId, "Переустановить бюджет"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, String.valueOf(newBudget)));
    }

    @Test
    void testPositivelyResetBudget() {
        float newBudget = 100;
        String resultMessage = String.format("Отлично, Вы переустановили ваш " + "ежемесячный бюджет. Он составляет %s рублей", newBudget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId, "Переустановить бюджет"));
        assertEquals(resultMessage, actionsHandler.processUserMessage(userId, String.valueOf(newBudget)));
    }

    @Test
    void testGenerateCommandError() {
        assertEquals(commandError, actionsHandler.processUserMessage(userId, "/какая-то неизвестная команда"));
    }

    @Test
    void testCheckCategories() {
        SetBudget setBudget = new SetBudget();
        DecreaseBudget decreaseBudget = new DecreaseBudget();
        float budget = 1000;
        setBudget.execute(user, String.valueOf(budget));
        decreaseBudget.execute(user, productsCosts);
        var sum = user.getCategories().stream().filter(x -> x.name.equals("Продукты")).findFirst().get();
        assertEquals(200, sum.getAmountSpent());
    }

    @Test
    void testAddCategory() {
        AddCategory addCategory = new AddCategory();
        addCategory.execute(user, "Прочее");
        assertTrue(user.getCategories().stream().anyMatch(x -> x.name.equals("Прочее")));
    }

    @Test
    void testCheckFunctionality() {
        user.setMonthBudget(budget);
        assertEquals(budget, user.checkMonthBudget());
        float increase = 5000;
        user.increaseMonthBudget(increase);
        user.decreaseWithCategory("Продукты, 1000");
        assertEquals(1000, user.getCategories().stream().filter(x -> x.name.equals("Продукты")).findFirst().get().getAmountSpent());
        user.addCategory(new Category("Прочее", 0));
        assertTrue(user.getCategories().stream().anyMatch(x -> x.name.equals("Прочее")));
        user.decreaseWithCategory("Прочее, 500");
        assertEquals(4500.0, user.checkMonthBudget());
        user.resetMonthBudget(20000f);
        assertEquals(20000f, user.checkMonthBudget());
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
}
