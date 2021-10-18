import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ActionsHandler {

  private static HashMap<String, ICommand> commands;

  public ActionsHandler() {
    commands = new HashMap<>();
    commands.put("/start", this::startProcess);
    commands.put("/get_strategies", this::getStrategies);
  }

  public static String processUserMessage(String message) {
    if ((message != null) & (commands.containsKey(message))) {
      return commands.get(message).performFunction();
    }
    return generateError();
  }

  public String startProcess() {
    return readFileContent("start.txt");
  }

  public static String generateError() {
    return "Я не знаю такую команду:(";
  }

  public String getStrategies() {
    return "Стратегия 1\n Стратегия 2\n Стратегия 3";
  }

  public static String readFileContent(String filename) {
    try {
      Scanner scanner = new Scanner(new File(String.format("src%1$smain%1$sresources%1$s%2$s",
          File.separator, filename)));
      scanner.useDelimiter("\\Z");
      return scanner.next();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
