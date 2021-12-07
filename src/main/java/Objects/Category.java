package Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users_categories")
public class Category {
    @Column(name = "category")
    public String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_database_id")
    private User user;

    @Column(name = "amount_spent")
    private float amountSpent;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DateSpent> dateSpentList;

    public Category() {
    }

    public Category(String name, float amountSpent) {
        this.name = name;
        this.amountSpent = amountSpent;
        dateSpentList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        name = categoryName;
    }

    public float getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(float amountSpent) {
        this.amountSpent = amountSpent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return String.format("%s: %s", name, amountSpent);
    }

    public void increaseAmountSpent(float sum) {
        if (user.getMonthBudget() > sum && sum > 0)
            amountSpent += sum;
    }

    public List<DateSpent> getDateSpentList() {
        return dateSpentList;
    }

    public void setDateSpentList(ArrayList<DateSpent> dateSpentList) {
        this.dateSpentList = dateSpentList;
    }

    public void addDateSpent(Date date, float sum) {
        if (sum < 0 || user.getMonthBudget() < sum) return;
        if (dateSpentList.stream().anyMatch(x -> x.getDate().equals(date)))
            dateSpentList.stream().filter(x -> x.getDate().equals(date))
                    .findFirst().get().addSpent(sum);
        else {
            DateSpent newSpent = new DateSpent(date, sum);
            newSpent.setCategory(this);
            dateSpentList.add(newSpent);
        }

    }
}