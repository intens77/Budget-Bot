import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Test_Bot {

  FinanceBot bot;
  String globalMessage;

  @BeforeEach
  void setUp() {
    bot = new FinanceBot();
    try (BufferedReader reader = new BufferedReader(
        new FileReader("/home/vladislav/Рабочий стол/TelegramBot/src/main/resources/help.txt"))) {
      StringBuilder helpMessage = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        helpMessage.append(line);
        helpMessage.append("\n\n");
      }
      globalMessage = helpMessage.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void checkStart() {
    assertEquals(globalMessage, bot.startProcess());
  }

  @Test
  void testGetStrategy() {
    assertEquals("Стратегия 1\n Стратегия 2\n Стратегия 3", bot.getStrategies());
  }

  @Test
  void testGenerateError() {
    assertEquals("Я не знаю такую команду:(", FinanceBot.generateError());
  }
}
