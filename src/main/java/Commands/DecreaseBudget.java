package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import WorkingClasses.User;

import java.util.ArrayList;

public class DecreaseBudget extends Command {
    public DecreaseBudget() {
        limitParameter = 2;
        parameters = new ArrayList<>();
    }

    public String execute(User user, String message) {
        var operationResult = user.decreaseWithCategory(message);
        if (operationResult)
            return String.format("Отлично, Вы уменьшили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return ServiceFunctions.generateCommandParameterError();
    }
}
