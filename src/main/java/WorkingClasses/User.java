package WorkingClasses;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import Patterns.Command;

public class User {

    private final String userId;
    private float monthBudget;
    private Command commandCall = null;
    private final HashMap<String, Float> categories = new HashMap<>();

    private String lastCategory = null;

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
        if (categories.containsKey(split_message[0])){
            categories.put(split_message[0], categories.get(split_message[0]) + sum);
            return decreaseMonthBudget(sum);
        }
        else return false;
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

    public void setLastUserCommand(Command lastCalledCommand) {
        commandCall = lastCalledCommand;
    }

    public Command getLastUserCommand() {
        return commandCall;
    }

    public String getLastCategory() {
        var commandCall = lastCategory;
        lastCategory = null;
        return commandCall;
    }

    public void setLastCategory(String lastCategory) {
        this.lastCategory = lastCategory;
    }
}
