import java.util.HashMap;
import java.util.Stack;

public class User {

    private final String userId;
    private float monthBudget;
    private ICommand commandCall = null;
    private final HashMap<String, Float> categories = new HashMap<>();

    public User(String userId) {
        this.userId = userId;
        categories.put("Транспорт", 0F);
        categories.put("Продукты", 0F);
        categories.put("Кафе", 0F);
    }

    public String getUserId() {
        return userId;
    }

    public boolean setMonthBudget(float budget) {
        if (budget > 0) {
            monthBudget = budget;
            return true;
        }
        return false;
    }

    public boolean resetMonthBudget(float budget) {
        return setMonthBudget(budget);
    }

    public boolean increaseMonthBudget(float sum) {
        if (sum > 0) {
            monthBudget += sum;
            return true;
        }
        return false;
    }

    public boolean decreaseMonthBudget(float sum) {
        if (sum > 0 & monthBudget >= sum) {
            monthBudget -= sum;
            return true;
        }
        return false;
    }

    public boolean decreaseWithCategory(String message){
        var split_message = message.split(" ");
        var sum = Float.parseFloat(split_message[1]);
        categories.put(split_message[0], categories.get(split_message[0]) + sum);
        return decreaseMonthBudget(sum);
    }

    public float checkMonthBudget() {
        return this.monthBudget;
    }

    public HashMap<String, Float> getCategories(){
        return categories;
    }

    public void addCategory(String message) {
        categories.put(message, 0F);
    }

    public void push(ICommand lastCalledCommand) {
        commandCall = lastCalledCommand;
    }

    public ICommand pop() {
        var command = commandCall;
        commandCall = null;
        return command;
    }
}
