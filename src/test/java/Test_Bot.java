import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test_Bot {

    BudgetBot bot;
    String message;
    ActionsHandler actionsHandler;

    @BeforeEach
    void setUp() {
        bot = new BudgetBot();
        actionsHandler = new ActionsHandler();
        message = SecondaryFunctions.readFileContent("start.txt");
    }

    @Test
    void checkStart() {
        assertEquals(message, actionsHandler.processUserMessage("/start"));
    }

    @Test
    void testGetStrategy() {
        assertEquals("Стратегия 1\n Стратегия 2\n Стратегия 3",
                actionsHandler.processUserMessage("/get_strategies"));
    }

    @Test
    void testGenerateError() {
        assertEquals("Я не знаю такую команду:(", actionsHandler.generateError());
    }
}
