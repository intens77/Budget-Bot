import java.util.HashMap;

public class User {

  private String userChatId;
  private float monthBudget;
  private static HashMap<String, Integer> expenses;

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
  }

  public void setMonthBudget(float sum) {
    if (sum > 0) {
      this.monthBudget = sum;
    }
  }

  public float getMonthBudget() {
    return this.monthBudget;
  }
}
