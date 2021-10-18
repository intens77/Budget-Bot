import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test_Bot {

  FinanceBot bot;
  String message;

  @BeforeEach
  void setUp() {
    bot = new FinanceBot();
    message = ActionsHandler.readFileContent("start.txt");
  }

  @Test
  void checkStart() {
    assertEquals(message, ActionsHandler.processUserMessage("/start"));
  }

  @Test
  void testGetStrategy() {
    assertEquals("Стратегия 1\n Стратегия 2\n Стратегия 3",
        ActionsHandler.processUserMessage("/get_strategies"));
  }

  @Test
  void testGenerateError() {
    assertEquals("Я не знаю такую команду:(", ActionsHandler.generateError());
  }
}
