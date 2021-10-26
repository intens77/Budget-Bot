import java.util.HashMap;
import java.util.Objects;


public class ActionsHandler {

  private static HashMap<String, ICommand> commands;
  private static HashMap<String, IStrategy> strategies;
  private static String strategy;
  private Integer Finance;
  private HashMap<String, User> users;


//  public HashMap<String, ICommand> getCommands()
//  {
//    return commands;
//  }

  public ActionsHandler() {
    commands = new HashMap<>();
    commands.put("/start", this::startProcess);
    commands.put("/get_strategies", this::getStrategies);
//    commands.put("/statistic", this::get_statistic);
    strategies = new HashMap<>();
//    strategies.put("/add", User::add_money);
//    strategies.put("/sub", User::sub_money);
    Finance = 0;
    users = new HashMap<>();
    strategy = "";
  }

  public String processUserMessage(String message, String id) {
    if (!users.containsKey(id))
      users.put(id, new User(id));
    if ((message != null) & (commands.containsKey(message))) {
      return commands.get(message).execute();
    }
    else if (users.get(id).strategies.containsKey(message)){
      strategy = message;
      return "Введите сумму";
    }
    else if (!Objects.equals(strategy, "")) {
      String result = "Ваш бюджет: " + users.get(id).strategies.get(strategy).execute(message);
      strategy = "";
      return result;
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
    return "Стратегия 1\nСтратегия 2\nСтратегия 3";
  }

//  public String add_money(String message)
//  {
//      strategy = "";
//      Finance += Integer.parseInt(message);
//      return Finance.toString();
//  }
//
//  public String sub_money(String message)
//  {
//    String[] mes = message.split(" ");
//    strategy = "";
//    int expense = Integer.parseInt(mes[1]);
//    user.setExpenses(mes[0], expense);
//    Finance -= expense;
//    return Finance.toString();
//  }

//  public String get_statistic(){
//    String statistic = "";
//    statistic += "У вас осталось" + Finance.toString() + "\n" + user.getExpenses().toString();
//    return statistic;
//  }
}
