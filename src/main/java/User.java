public class User {

  private String userChatId;
  private float monthBudget;

  public User(String chatId) {
    userChatId = chatId;
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
