import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    public String parameterRequestMessage;
    public String commandErrorMessage;
    public String parameterErrorMessage;

    @BeforeEach
    void setUp() {
        parameterRequestMessage = "Сумма:";
        commandErrorMessage = ActionsHandler.generateCommandError();
        parameterErrorMessage = ActionsHandler.generateCommandParameterError();
    }

    @Test
    void testStartProcess() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        String result = SecondaryFunctions.readFileContent("start.txt");
        assertEquals(result, actionsHandler.processUserMessage(user.getUserId(),
                "/start"));
    }

    @Test
    void testGetStrategies() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        String result = "Стратегия 1\n Стратегия 2\n Стратегия 3";
        assertEquals(result,
                actionsHandler.processUserMessage(user.getUserId(),
                        "/get_strategies"));
    }

    @Test
    void testNegativelySetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float budget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/set_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(user.getUserId(),
                String.valueOf(budget)));
    }

    @Test
    void testPositivelySetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float budget = 100;
        String resultMessage = String.format("Отлично, Вы установили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", budget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/set_budget"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(user.getUserId(), String.valueOf(budget)));
    }

    @Test
    void testNegativelyIncreaseBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float increase = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/increase_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(user.getUserId(),
                String.valueOf(increase)));
    }

    @Test
    void testPositivelyIncreasePositiveBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float increase = 100;
        String resultMessage = String.format("Отлично, Вы увеличили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", increase);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/increase_budget"));
        assertEquals(resultMessage, actionsHandler.processUserMessage(user.getUserId(),
                String.valueOf(increase)));
    }

    @Test
    void testNegativelyDecreaseBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float decrease = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/decrease_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(user.getUserId(),
                String.valueOf(decrease)));
    }

    @Test
    void testPositivelyDecreasePositiveBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float budget = 300;
        float decrease = 100;
        actionsHandler.processUserMessage(user.getUserId(), "/set_budget");
        actionsHandler.processUserMessage(user.getUserId(), String.valueOf(budget));
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/decrease_budget"));
        String expected = String.format("Отлично, Вы уменьшили ваш " +
                        "ежемесячный бюджет. Он составляет %s рублей",
                budget - decrease);
        String actual = actionsHandler.processUserMessage(user.getUserId(),
                String.valueOf(decrease));
        assertEquals(expected, actual);
    }

    @Test
    void testNegativelyResetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float newBudget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/reset_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(user.getUserId(),
                String.valueOf(newBudget)));
    }

    @Test
    void testPositivelyResetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        float newBudget = 100;
        String resultMessage = String.format("Отлично, Вы переустановили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", newBudget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/reset_budget"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(user.getUserId(), String.valueOf(newBudget)));
    }

    @Test
    void testCheckBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        String resultMessage = String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
        assertEquals(resultMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/check_budget"));
    }

    @Test
    void testGenerateCommandError() {
        ActionsHandler actionsHandler = new ActionsHandler();
        User user = new User(System.getenv("MY_CHAT_ID"));
        assertEquals(commandErrorMessage, actionsHandler.processUserMessage(user.getUserId(),
                "/какая-то неизвестная команда"));
    }
}
