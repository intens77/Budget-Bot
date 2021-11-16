import WorkingClasses.ActionsHandler;
import WorkingClasses.ServiceFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandsTests {
    private String parameterRequestMessage;
    private String parameterErrorMessage;
    private String myChatId;
    private String commandError;

    @BeforeEach
    void setUp() {
        parameterRequestMessage = "Сумма:";
        myChatId = System.getenv("MY_CHAT_ID");
        parameterErrorMessage = ServiceFunctions.generateCommandParameterError();
        commandError = ServiceFunctions.generateCommandError();
    }

    @Test
    void testStartProcess() {
        ActionsHandler actionsHandler = new ActionsHandler();
        String result = ServiceFunctions.readFileContent("start.txt");
        assertEquals(result, actionsHandler.processUserMessage(myChatId,
                "/start"));
    }

    @Test
    void testGetStrategies() {
        ActionsHandler actionsHandler = new ActionsHandler();
        String result = "Стратегия 1\n Стратегия 2\n Стратегия 3";
        assertEquals(result, actionsHandler.processUserMessage(myChatId, "/get_strategies"));
    }

    @Test
    void testNegativelySetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float budget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/set_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(myChatId,
                String.valueOf(budget)));
    }

    @Test
    void testPositivelySetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float budget = 100;
        String resultMessage = String.format("Отлично, Вы установили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", budget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/set_budget"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(myChatId, String.valueOf(budget)));
    }

    @Test
    void testNegativelyIncreaseBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float increase = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/increase_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(myChatId,
                String.valueOf(increase)));
    }

    @Test
    void testPositivelyIncreasePositiveBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float increase = 100;
        String resultMessage = String.format("Отлично, Вы увеличили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", increase);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/increase_budget"));
        assertEquals(resultMessage, actionsHandler.processUserMessage(myChatId,
                String.valueOf(increase)));
    }

    @Test
    void testNegativelyDecreaseBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float decrease = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/decrease_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(myChatId,
                String.valueOf(decrease)));
    }

    @Test
    void testPositivelyDecreasePositiveBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float budget = 300;
        float decrease = 100;
        actionsHandler.processUserMessage(myChatId, "/set_budget");
        actionsHandler.processUserMessage(myChatId, String.valueOf(budget));
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/decrease_budget"));
        String expected = String.format("Отлично, Вы уменьшили ваш " +
                        "ежемесячный бюджет. Он составляет %s рублей",
                budget - decrease);
        String actual = actionsHandler.processUserMessage(myChatId,
                String.valueOf(decrease));
        assertEquals(expected, actual);
    }

    @Test
    void testNegativelyResetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float newBudget = -100;
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/reset_budget"));
        assertEquals(parameterErrorMessage, actionsHandler.processUserMessage(myChatId,
                String.valueOf(newBudget)));
    }

    @Test
    void testPositivelyResetBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        float newBudget = 100;
        String resultMessage = String.format("Отлично, Вы переустановили ваш " +
                "ежемесячный бюджет. Он составляет %s рублей", newBudget);
        assertEquals(parameterRequestMessage, actionsHandler.processUserMessage(myChatId,
                "/reset_budget"));
        assertEquals(resultMessage,
                actionsHandler.processUserMessage(myChatId, String.valueOf(newBudget)));
    }

    @Test
    void testCheckBudget() {
        ActionsHandler actionsHandler = new ActionsHandler();
        String resultMessage = String.format("Ваш текущий бюджет: %s", (float) 0);
        assertEquals(resultMessage, actionsHandler.processUserMessage(myChatId,
                "/check_budget"));
    }

    @Test
    void testGenerateCommandError() {
        ActionsHandler actionsHandler = new ActionsHandler();
        assertEquals(commandError, actionsHandler.processUserMessage(myChatId,
                "/какая-то неизвестная команда"));
    }
}
