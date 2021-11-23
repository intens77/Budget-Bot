package Commands;

import Objects.User;
import Patterns.Command;


public class CheckBudget extends Command {
    @Override
    public String execute(User user, String message) {
        return String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
    }
}
