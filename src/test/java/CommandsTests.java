import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import Commands.*;
import WorkingClasses.*;

public class CommandsTests {
    public String parameterRequestMessage;
    public String commandError;
    public String parameterErrorMessage;
    public ActionsHandler actionsHandler;
    public String userId;
    public float budget;
    public String productsCosts;
    public String cafeCosts;

    @BeforeEach
    void setUp() {
        parameterRequestMessage = "Введите сумму или параметры:";
        actionsHandler = new ActionsHandler();
        userId = System.getenv("MY_CHAT_ID");
        budget = 1000;
        productsCosts = "Продукты 200";
        cafeCosts = "Кафе 500";
        parameterErrorMessage = ServiceFunctions.generateCommandParameterError();
        commandError = ServiceFunctions.generateCommandError();
    }

    @Test
    void testStartProcess() {
        String result = ServiceFunctions.readFileContent("start.txt");
        assertEquals(result, actionsHandler.processUserMessage(userId,
                "/start"));
    }

    @Test
    void testGetStrategies() {
        String result = "Стратегия 1\n Стратегия 2\n Стратегия 3";
        assertEquals(result,
                actionsHandler.processUserMessage(userId,
                        "/get_strategies"));
    }

    @Test
    void testNegativelySetBudget() {
        float budget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/set_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId,
                String.valueOf(budget)));
    }

    @Test
    void testPositivelySetBudget() {
        float budget = 100;
        String resultMessage = String.format("Отлично, Вы установили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", budget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/set_budget"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(userId, String.valueOf(budget)));
    }

    @Test
    void testNegativelyIncreaseBudget() {
        float increase = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/increase_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId,
                String.valueOf(increase)));
    }

    @Test
    void testPositivelyIncreasePositiveBudget() {
        float increase = 100;
        String resultMessage = String.format("Отлично, Вы увеличили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", increase);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/increase_budget"));
        assertEquals(resultMessage, actionsHandler.processUserMessage(userId,
                String.valueOf(increase)));
    }

    @Test
    void testNegativelyDecreaseBudget() {
        String decrease = "Кафе 100";
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/decrease_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, decrease));
    }

    @Test
    void testPositivelyDecreasePositiveBudget() {
        String decrease = "Продукты 100";
        actionsHandler.processUserMessage(userId, "/set_budget");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/decrease_budget"));
        String expected = String.format("Отлично, Вы уменьшили ваш " +
                        "ежемесячный бюджет. Он составляет %s рублей",
                budget - Float.parseFloat(decrease.split(" ")[1]));
        String actual = actionsHandler.processUserMessage(userId, decrease);
        assertEquals(expected, actual);
    }

    @Test
    void testNegativelyResetBudget() {
        float newBudget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/reset_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId,
                String.valueOf(newBudget)));
    }

    @Test
    void testPositivelyResetBudget() {
        float newBudget = 100;
        String resultMessage = String.format("Отлично, Вы переустановили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", newBudget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/reset_budget"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(userId, String.valueOf(newBudget)));
    }

    @Test
    void testCheckBudget() {
        User user = new User(userId);
        String resultMessage = String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
        assertEquals(resultMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/check_budget"));
    }

    @Test
    void testGenerateCommandError() {
        assertEquals(commandError, actionsHandler.processUserMessage(userId,
                "/какая-то неизвестная команда"));
    }
    @Test
    void testCheckCategories() {
        User user = new User(System.getenv("MY_CHAT_ID"));
        SetBudget setBudget = new SetBudget(1);
        DecreaseBudget decreaseBudget = new DecreaseBudget(2);
        float budget = 1000;
        String productsCosts = "Продукты 200";
        setBudget.execute(user, String.valueOf(budget));
        decreaseBudget.execute(user, productsCosts);
//        actionsHandler.processUserMessage(userId, String.valueOf(budget));//setBudget(user, String.valueOf(budget));
//        actionsHandler.decreaseBudget(user, productsCosts);
        assertEquals(200, (float) user.getCategories().get("Продукты"));
    }
    @Test
    void testWrongCategory() {
        actionsHandler.processUserMessage(userId, "/set_budget");
        actionsHandler.processUserMessage(userId, "10000");
        actionsHandler.processUserMessage(userId, "/decrease_budget");
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, "qwerty 1000"));
    }
    @Test
    void testAddCategory() {
        User user = new User(userId);
        AddCategory addCategory = new AddCategory();
        addCategory.execute(user, "Прочее");
//        actionsHandler.addCategory(user, "/add_category");
//        actionsHandler.addCategory(user, "Прочее");
        assertTrue(user.getCategories().containsKey("Прочее"));
    }
//    @Test
//    void testCheckStatistic() {
//        User user = new User(userId);
//        actionsHandler.setBudget(user, String.valueOf(budget));
//        actionsHandler.decreaseBudget(user, productsCosts);
//        actionsHandler.decreaseBudget(user, cafeCosts);
//        String result = "Ваши расходы по категориям:\n" +
//                "Транспорт: 0.0\n" + "Продукты: 200.0\n" + "Кафе: 500.0\n" +
//                "Всего вы потратили: 700.0";
//        assertEquals(result, actionsHandler.checkStatistic(user, "/check_stat"));
//    }
    @Test
    void testCheckFunctionality() {
        User user = new User(userId);
        user.setMonthBudget(budget);
        assertEquals(budget, user.checkMonthBudget());
        float increase = 5000;
        user.increaseMonthBudget(increase);
        user.decreaseWithCategory("Продукты 1000");
        assertEquals(1000, user.getCategories().get("Продукты"));
        user.addCategory("Прочее");
        assertTrue(user.getCategories().containsKey("Прочее"));
        user.decreaseWithCategory("Прочее 500");
        assertEquals(4500.0, user.checkMonthBudget());
        user.resetMonthBudget(20000f);
        assertEquals(20000f, user.checkMonthBudget());
    }
}
