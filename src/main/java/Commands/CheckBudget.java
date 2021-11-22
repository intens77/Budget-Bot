package Commands;

import Patterns.Command;
import WorkingClasses.User;

public class CheckBudget extends Command {
    public CheckBudget(Integer limitParameter) {
        super(limitParameter);
    }

    @Override
    public String execute(User user, String message) {
        return String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
    }
}
