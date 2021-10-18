import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


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

  }

  @Test
  void checkStart() {
    assertEquals(globalMessage, bot.startProcess());
  }

  @Test
  void testGetStrategy() {
//    when(strategyCommand.execute()).thenReturn();
//    assertEquals("Стратегия 1\n Стратегия 2\n Стратегия 3", bot.getStrategies());
  }

  @Test
  void testGenerateError() {
    assertEquals("Я не знаю такую команду:(", FinanceBot.generateError());
  }
}
