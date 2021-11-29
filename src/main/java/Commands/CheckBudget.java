package Commands;

import Patterns.Command;
import WorkingClasses.User;

import java.util.ArrayList;

public class CheckBudget extends Command {
    public CheckBudget() {
        limitParameter = 0;
        parameters = new ArrayList<>();
    }

    @Override
    public String execute(User user, String message) {
        return String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
    }
}
