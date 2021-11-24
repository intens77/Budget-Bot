package WorkingClasses;

public class Category {
    public final String name;
    private float amountSpent;
    private User user;

    public Category(String name, float amountSpent, User user){
        this.name = name;
        this.amountSpent = amountSpent;
        this.user = user;
    }

    public float getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(float sum) {
        amountSpent += sum;
    }

    public String toString() {
        return String.format("%s: %s", name, amountSpent);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
