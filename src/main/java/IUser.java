public interface IUser {
    boolean setMonthBudget(float budget);

    boolean resetMonthBudget(float budget);

    boolean increaseMonthBudget(float sum);

    boolean decreaseMonthBudget(float sum);

    float checkMonthBudget();

}
