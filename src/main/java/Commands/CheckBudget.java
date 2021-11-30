package Commands;

import Objects.User;
import Patterns.Command;

import java.util.ArrayList;

public class CheckBudget extends Command {
    public CheckBudget() {
        parameters = new ArrayList<>();
        limitParameter = 0;
    }

    @Override
    public String execute(User user, String message) {
        return String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
    }
}
