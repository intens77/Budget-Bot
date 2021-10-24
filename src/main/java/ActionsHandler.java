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

  public String processUserMessage(String message) {
    if ((message != null) & (commands.containsKey(message))) {
      return commands.get(message).execute();
    }
    return generateError();
  }

  public String startProcess() {
    return SecondaryFunctions.readFileContent("start.txt");
  }

  public String generateError() {
    return "Я не знаю такую команду:(";
  }

  public String getStrategies() {
    return "Стратегия 1\n Стратегия 2\n Стратегия 3";
  }

}
