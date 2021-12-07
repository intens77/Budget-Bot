package Commands;

import Objects.User;
import Patterns.Command;
import WorkingClasses.ServiceFunctions;

import java.util.ArrayList;

public class DecreaseBudget extends Command {
    public DecreaseBudget() {
        parameters = new ArrayList<>();
        limitParameter = 2;
    }

    public String execute(User user, String message) {
        boolean operationResult = user.decreaseWithCategory(message);
        if (operationResult) {
            return String.format("Отлично, Вы уменьшили ваш " + "ежемесячный бюджет. Он составляет %s рублей", user.checkMonthBudget());
        }
        return ServiceFunctions.generateCommandParameterError();
    }
}
