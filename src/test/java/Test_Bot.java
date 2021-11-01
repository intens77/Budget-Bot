import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class Test_Bot {

  FinanceBot bot;
  String globalMessage;
  Receiver receiver;
  ICommand mCommand;
  @InjectMocks
  StartCommand start;
  @InjectMocks
  StrategyCommand strategyCommand;

  @BeforeEach
  void setUp() {
    bot = new FinanceBot();
    receiver = new Receiver();
    mCommand = Mockito.mock(ICommand.class);
    start = new StartCommand(receiver);
    strategyCommand = new StrategyCommand(receiver);

=======
public class Test_Bot {

  BudgetBot bot;
  String message;
  ActionsHandler actionsHandler;

  @BeforeEach
  void setUp() {
    bot = new BudgetBot();
    actionsHandler = new ActionsHandler();
    message = SecondaryFunctions.readFileContent("start.txt");
>>>>>>> b7f289c27da066b32f2fd3b8cd894a8ef8b7fbda
  }

  @Test
  void checkStart() {
    assertEquals(message, actionsHandler.processUserMessage("/start", "1"));
  }

  @Test
  void testGetStrategy() {
<<<<<<< HEAD
//    when(strategyCommand.execute()).thenReturn();
//    assertEquals("Стратегия 1\n Стратегия 2\n Стратегия 3", bot.getStrategies());
=======
    assertEquals("Стратегия 1\n Стратегия 2\n Стратегия 3",
            actionsHandler.processUserMessage("/get_strategies", "1"));
>>>>>>> b7f289c27da066b32f2fd3b8cd894a8ef8b7fbda
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
