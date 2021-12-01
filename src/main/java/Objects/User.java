package Objects;


import WorkingClasses.EntityManager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "telegram_id")
    private String telegramId;

    @Column(name = "month_budget")
    private float monthBudget;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> userCategories;

    public User() {
    }

    public User(String telegramId) {
        this.telegramId = telegramId;
        userCategories = new ArrayList<>();
    }

    public boolean setMonthBudget(float budget) {
        if (budget > 0) {
            monthBudget = budget;
            EntityManager.updateUser(this);
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
            EntityManager.updateUser(this);
            return true;
        }
        return false;
    }

    public boolean decreaseMonthBudget(float sum) {
        if (sum > 0 & monthBudget >= sum) {
            monthBudget -= sum;
            EntityManager.updateUser(this);
            return true;
        }
        return false;
    }

    public float checkMonthBudget() {
        return this.monthBudget;
    }

    public void addCategory(Category category) {
        category.setUser(this);
//        EntityManager.saveCategory(category);
        userCategories.add(category);
        EntityManager.updateUser(this);
    }

    public void removeCategory(Category category) {
//        EntityManager.deleteCategory(category);
        userCategories.remove(category);
        EntityManager.updateUser(this);
    }

    public int getId() {
        return id;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public float getMonthBudget() {
        return monthBudget;
    }

    public void setCategories(List<Category> categories) {
        userCategories = categories;
        EntityManager.updateUser(this);
    }

    public boolean decreaseWithCategory(String message) {
        var split_message = message.split(", ");
        var category = split_message[0];
        var sum = Float.parseFloat(split_message[1]);
        if (!containsCategory(category))
            addCategory(new Category(category, 0));
        var cur = userCategories.stream().filter(x -> x.name.equals(category)).findFirst().get();
        cur.increaseAmountSpent(sum);
        return decreaseMonthBudget(sum);
    }

    public List<Category> getCategories() {
        return userCategories;
    }

    public ArrayList<String> getCategoriesName() {
        return userCategories.stream().map(x -> x.name).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean containsCategory(String message) {
        return userCategories.stream().anyMatch(category -> category.name.equals(message));
    }
}
