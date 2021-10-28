public class User {

    private final String userId;
    private float monthBudget;

    public User(String userId) {
        this.userId = userId;
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
        if (sum > 0) {
            monthBudget -= sum;
            return true;
        }
        return false;
    }

    public float checkMonthBudget() {
        return this.monthBudget;
    }
}
