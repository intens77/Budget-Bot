package WorkingClasses;

import java.util.*;
import java.util.stream.Collectors;

public class User {

    private final String userId;
    private float monthBudget;
    private final ArrayList<Category> categories = new ArrayList<>();


    public User(String userId) {
        this.userId = userId;
        categories.add(new Category("Транспорт", 0f, this));
        categories.add(new Category("Продукты", 0f, this));
        categories.add(new Category("Кафе", 0f, this));
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
        var split_message = message.split(", ");
        var category = split_message[0];
        var sum = Float.parseFloat(split_message[1]);
        if (!containsCategory(category))
            addCategory(category);
        var cur = categories.stream().filter(x -> x.name.equals(category)).findFirst().get();
        cur.setAmountSpent(sum);
        return decreaseMonthBudget(sum);
    }

    public float checkMonthBudget() {
        return this.monthBudget;
    }

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public ArrayList<String> getCategoriesName() {
        return categories.stream().map(x -> x.name).collect(Collectors.toCollection(ArrayList::new));
    }

    public void addCategory(String message) {
        categories.add(new Category(message, 0f, this));
    }

    public boolean containsCategory(String message){
        return categories.stream().anyMatch(category -> category.name.equals(message));
    }
}
