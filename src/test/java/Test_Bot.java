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
    assertEquals(message, actionsHandler.processUserMessage("/start", "1"));
  }

  @Test
  void testGetStrategy() {
    assertEquals("Стратегия 1\n Стратегия 2\n Стратегия 3",
            actionsHandler.processUserMessage("/get_strategies", "1"));
  }

  @Test
  void testGenerateError() {
    assertEquals("Я не знаю такую команду:(", actionsHandler.processUserMessage("Something", "1"));
  }


  void testBudget(String command, String message){
//    message = String.valueOf((float)10000);
//    float budget = 10000;
    actionsHandler.processUserMessage(command, "1");
    assertEquals("Ваш бюджет: " + message + ".0", actionsHandler.processUserMessage(message, "1"));
  }

  @Test
  void testSet(){
    testBudget("/set_budget", "10000");
  }

  @Test
  void testIncrease(){
    testBudget("/increase_budget", "10000");
  }
}
