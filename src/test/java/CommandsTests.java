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

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandsTests {
    public static String parameterRequestMessage;
    public static String commandError;
    public static String parameterErrorMessage;
    public ActionsHandler actionsHandler;
    public User user;
    public String userId;
    public static float budget;
    public static String productsCosts;
    public static String cafeCosts;

    @BeforeEach
    void setUp() {
        actionsHandler = new ActionsHandler();
        userId = "42";
        user = new User(userId);
        actionsHandler.processUserMessage(userId, "Узнать бюджет");
    }

    @AfterEach
    void afterTest(){
        EntityManager.deleteUser(user);
    }

    @BeforeAll
    static void initBefore(){
        parameterRequestMessage = "Введите сумму";
        parameterErrorMessage = ServiceFunctions.generateCommandParameterError();
        commandError = ServiceFunctions.generateCommandError();
        budget = 1000;
        productsCosts = "Продукты, 200";
        cafeCosts = "Кафе, 500";
    }

    @AfterEach
    void deleteTestUser() {
        EntityManager.deleteUser(actionsHandler.getUsers().get(userId));
    }

    @Test
    void testStartProcess() {
        String result = ServiceFunctions.readFileContent("start.txt");
        assertEquals(result, actionsHandler.processUserMessage(userId,
                "старт"));
    }

    @Test
    void testNegativelySetBudget() {
        float budget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "Установить бюджет"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId,
                String.valueOf(budget)));
    }

    @Test
    void testPositivelySetBudget() {
        float budget = 100;
        String resultMessage = String.format("Отлично, Вы установили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", budget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "Установить бюджет"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(userId, String.valueOf(budget)));
    }

    @Test
    void testPositivelyIncreaseBudget() {
        float increase = 1000;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "Увеличить бюджет"));
        User currentUser = actionsHandler.getUsers().get(userId);
        String answer = String.format("Отлично, Вы увеличили ваш " + "ежемесячный бюджет. Он составляет %s рублей",  currentUser.checkMonthBudget() + increase);
        assertEquals(answer, actionsHandler.processUserMessage(userId,
                String.valueOf(increase)));
    }

    @Test
    void testNegativelyIncreaseBudget() {
        float increase = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "Увеличить бюджет"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, String.valueOf(increase)));
    }

    @Test
    void testNegativelyDecreaseBudget() {
        String decrease = "Кафе";
        String sum = "-100";
        String outMessage = new DecreaseBudget().getOutMessage();
        assertEquals(outMessage, actionsHandler.processUserMessage(userId,
                "Ввести расходы"));
        actionsHandler.processUserMessage(userId, decrease);
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, sum));
    }

    @Test
    void testPositivelyDecreasePositiveBudget() {
        String decrease = "Продукты";
        String sum = "100";
        actionsHandler.processUserMessage(userId, "Установить бюджет");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        String outMessage = new DecreaseBudget().getOutMessage();
        assertEquals(outMessage, actionsHandler.processUserMessage(userId,
                "Ввести расходы"));
        String expected = String.format("Отлично, Вы уменьшили ваш " +
                        "ежемесячный бюджет. Он составляет %s рублей",
                budget - Float.parseFloat(sum));
        actionsHandler.processUserMessage(userId, decrease);
        String actual = actionsHandler.processUserMessage(userId, sum);
        assertEquals(expected, actual);
    }

    @Test
    void testNegativelyResetBudget() {
        float newBudget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "Переустановить бюджет"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId,
                String.valueOf(newBudget)));
    }

    @Test
    void testPositivelyResetBudget() {
        float newBudget = 100;
        String resultMessage = String.format("Отлично, Вы переустановили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", newBudget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "Переустановить бюджет"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(userId, String.valueOf(newBudget)));
    }

    @Test
    void testGenerateCommandError() {
        assertEquals(commandError, actionsHandler.processUserMessage(userId,
                "/какая-то неизвестная команда"));
    }

    @Test
    void testCheckCategories() {
        actionsHandler.processUserMessage(userId, "Установить бюджет");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        actionsHandler.processUserMessage(userId, "Ввести расходы");
        actionsHandler.processUserMessage(userId, "Продукты");
        actionsHandler.processUserMessage(userId, "200");
        User currentUser = actionsHandler.getUsers().get(userId);
        var sum = currentUser.getCategories().stream().filter(x -> x.name.equals("Продукты")).findFirst().get();
        assertEquals(200, sum.getAmountSpent());
    }

    @Test
    void testAddCategory() {
        actionsHandler.processUserMessage(userId, "Добавить категорию расходов");
        actionsHandler.processUserMessage(userId, "Прочее");
        User currentUser = actionsHandler.getUsers().get(userId);
        assertTrue(currentUser.getCategories().stream().anyMatch(x -> x.name.equals("Прочее")));
    }

    String CheckTimeSpent(String date) {
        actionsHandler.processUserMessage(userId, "Установить бюджет");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        actionsHandler.processUserMessage(userId, "Ввести расходы");
        actionsHandler.processUserMessage(userId, "Прочее");
        actionsHandler.processUserMessage(userId, "300");
        assertEquals(new CheckTimeSpent().getOutMessage(),actionsHandler.processUserMessage(userId, "Проверить расходы"));
        return actionsHandler.processUserMessage(userId, date);
    }

    @Test
    void testCheckTimeSpent(){
        assertEquals("Прочее: 300.0\n", CheckTimeSpent(Date.valueOf(LocalDate.now()).toString()));
        assertEquals("Прочее: 600.0\n", CheckTimeSpent("Декабрь 2021"));
        assertEquals("Прочее: 900.0\n", CheckTimeSpent("Сегодня"));
        assertEquals("Прочее: 1200.0\n", CheckTimeSpent("Текущий месяц"));
        assertEquals("Прочее: 1500.0\n", CheckTimeSpent("Неделя"));
        assertEquals("Прочее: 1800.0\n", CheckTimeSpent("Все время"));
        assertEquals("Вы вели некорректное сообщение", CheckTimeSpent("6 Декабря 2021"));
    }

    @Test
    void testCheckTimeSpentUser(){
        actionsHandler.processUserMessage(userId, "Установить бюджет");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        Category category = new Category("Товары", 200);
        User currentUser = actionsHandler.getUsers().get(userId);
        category.setUser(currentUser);
        category.addDateSpent(Date.valueOf("2021-12-04"), 200);
        currentUser.addCategory(category);
        assertEquals("Товары: 200.0\n", new CheckTimeSpent().execute(currentUser, "2021-12-04"));
        category.addDateSpent(Date.valueOf("2021-12-06"), 400);
        assertEquals("Товары: 400.0\n", new CheckTimeSpent().execute(currentUser, "Вчера"));
    }
}
