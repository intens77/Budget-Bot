package Objects;

import javax.persistence.*;

@Entity
@Table(name = "users_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category")
    public String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_database_id")
    private User user;

    @Column(name = "amount_spent")
    private float amountSpent;

    public Category() {
    }

    public Category(String name, float amountSpent) {
        this.name = name;
        this.amountSpent = amountSpent;
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
}