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
        parameterRequestMessage = "Введите параметры:";
        actionsHandler = new ActionsHandler();
        userId = System.getenv("MY_CHAT_ID");
        budget = 1000;
        productsCosts = "Продукты, 200";
        cafeCosts = "Кафе, 500";
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
        String decrease = "Кафе";
        String sum = "-100";
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/decrease_budget"));
        actionsHandler.processUserMessage(userId, decrease);
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(userId, sum));
    }

    @Test
    void testPositivelyDecreasePositiveBudget() {
        String decrease = "Продукты";
        String sum = "100";
        actionsHandler.processUserMessage(userId, "/set_budget");
        actionsHandler.processUserMessage(userId, String.valueOf(budget));
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(userId,
                "/decrease_budget"));
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
        User user = new User(userId);
        AddCategory addCategory = new AddCategory();
        addCategory.execute(user, "Прочее");
        assertTrue(user.getCategories().stream().anyMatch(x -> x.name.equals("Прочее")));
    }
    @Test
    void testCheckStatistic() {
        User user = new User(userId);
        var setBudget = new SetBudget();
        var decreaseBudget = new DecreaseBudget();

        setBudget.execute(user, String.valueOf(budget));
        decreaseBudget.execute(user, productsCosts);
        decreaseBudget.execute(user, cafeCosts);
        String result = "Ваши расходы по категориям:\n" +
                "Транспорт: 0.0\n" + "Продукты: 200.0\n" + "Кафе: 500.0\n" +
                "Всего вы потратили: 700.0";
        assertEquals(result, new CheckStatistic().execute(user, "/check_stat"));
    }
    @Test
    void testCheckFunctionality() {
        User user = new User(userId);
        user.setMonthBudget(budget);
        assertEquals(budget, user.checkMonthBudget());
        float increase = 5000;
        user.increaseMonthBudget(increase);
        user.decreaseWithCategory("Продукты, 1000");
        assertEquals(1000, user.getCategories()
                .stream()
                .filter(x -> x.name.equals("Продукты")).
                findFirst().get().getAmountSpent());
        user.addCategory("Прочее");
        assertTrue(user.getCategories().stream().anyMatch(x -> x.name.equals("Прочее")));
        user.decreaseWithCategory("Прочее, 500");
        assertEquals(4500.0, user.checkMonthBudget());
        user.resetMonthBudget(20000f);
        assertEquals(20000f, user.checkMonthBudget());
    }

    @Test
    void testCheckTwoUsers(){
        actionsHandler.processUserMessage("1", "/set_budget");
        actionsHandler.processUserMessage("2", "/set_budget");
        actionsHandler.processUserMessage("2", "10000");
        actionsHandler.processUserMessage("1", "15000");
        assertEquals(10000, actionsHandler.getUsers().get("2").checkMonthBudget());
        assertEquals(15000, actionsHandler.getUsers().get("1").checkMonthBudget());
    }
}
