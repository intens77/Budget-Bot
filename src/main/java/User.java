import java.util.HashMap;

public class User {

  private String userChatId;
  private float monthBudget;
  private static HashMap<String, Integer> expenses;
  public HashMap<String, IStrategy> strategies;

  public HashMap<String, Integer> getExpenses(){
    return expenses;
  }

  public void setExpenses(String message, int num){
    if (expenses.containsKey(message))
      expenses.put(message, expenses.get(message) + num);
  }

  public User(String chatId) {
    userChatId = chatId;
    monthBudget = 0;
    expenses = new HashMap<>();
    expenses.put("Продукты", 0);
    expenses.put("Транспорт", 0);
    expenses.put("Здоровье", 0);
    strategies = new HashMap<>();
    strategies.put("/increase_budget", this::Increase_budget);
    strategies.put("/decrease_budget", this::decreaseBudget);
    strategies.put("/set_budget", this::setBudget);
    strategies.put("/reset_budget", this::resetBudget);
  }

  public void setMonthBudget(float sum) {
    if (sum > 0) {
      this.monthBudget = sum;
    }
  }

  public float getMonthBudget() {
    return this.monthBudget;
  }

  public String Increase_budget(String message)
  {
    monthBudget+= Integer.parseInt(message);
    return String.valueOf(monthBudget);
  }

  public String decreaseBudget(String message)
  {
//    String[] mes = message.split(" ");
//    int expense = Integer.parseInt(mes[1]);
//    setExpenses(mes[0], expense);

    monthBudget-= Integer.parseInt(message);
    return String.valueOf(monthBudget);
  }

  public String setBudget(String message){
    monthBudget = Integer.parseInt(message);
    return String.valueOf(monthBudget);
  }
  public String resetBudget(String message){
    return setBudget(message);
  }
}
