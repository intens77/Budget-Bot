import java.util.HashMap;
import java.util.Objects;


public class ActionsHandler {

  private static HashMap<String, ICommand> commands;
  private static HashMap<String, IStrategy> strategies;
  private static String strategy;
  private Integer Finance;
  public User user;


//  public HashMap<String, ICommand> getCommands()
//  {
//    return commands;
//  }

  public ActionsHandler() {
    commands = new HashMap<>();
    commands.put("/start", this::startProcess);
    commands.put("/get_strategies", this::getStrategies);
    commands.put("/statistic", this::get_statistic);
    strategies = new HashMap<>();
    strategies.put("/add", this::add_money);
    strategies.put("/sub", this::sub_money);
    Finance = 0;
    user = new User("1");
  }

  public String processUserMessage(String message) {
    if ((message != null) & (commands.containsKey(message))) {
      return commands.get(message).execute();
    }
    else if (strategies.containsKey(message)){
      strategy = message;
      return "Введите сумму";
    }
    else if (!Objects.equals(strategy, "")) {
      return strategies.get(strategy).execute(message);
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

  public String add_money(String message)
  {
      strategy = "";
      Finance += Integer.parseInt(message);
      return Finance.toString();
  }

  public String sub_money(String message)
  {
    String[] mes = message.split(" ");
    strategy = "";
    int expense = Integer.parseInt(mes[1]);
    user.setExpenses(mes[0], expense);
    Finance -= expense;
    return Finance.toString();
  }

  public String get_statistic(){
    String statistic = "";
    statistic += "У вас осталось" + Finance.toString() + "\n" + user.getExpenses().toString();
    return statistic;
  }
}
