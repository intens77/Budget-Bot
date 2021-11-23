package Objects;

import javax.persistence.*;

@Entity
@Table(name = "users_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int databaseId;

    @Column(name = "category")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "database_id")
    private User user;

    private float amountSpent;

    public Category() {
    }

    public Category(String name, float amountSpent) {
        this.name = name;
        this.amountSpent = amountSpent;
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
}