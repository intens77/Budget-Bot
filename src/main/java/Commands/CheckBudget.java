package Commands;

import Objects.User;
import Patterns.Command;


public class CheckBudget extends Command {
    private static final int limitParameter = 0;

    @Override
    public String execute(User user, String message) {
        return String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
    }
}
