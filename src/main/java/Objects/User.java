package Objects;


import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int databaseId;

    @Column(name = "telegram_id")
    private String telegramId;

    private float monthBudget;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Category> userCategories;

    public User() {
    }

    public User(String telegramId) {
        this.telegramId = telegramId;
        userCategories = new ArrayList<>();
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

    public float checkMonthBudget() {
        return this.monthBudget;
    }

    public void addNewCategory(Category category) {
        category.setUser(this);
        userCategories.add(category);
    }

    public void removeCategory(Category category) {
        userCategories.remove(category);
    }

    public int getId() {
        return databaseId;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public ArrayList<Category> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(ArrayList<Category> categories) {
        userCategories = categories;
    }
}
