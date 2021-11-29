package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import WorkingClasses.User;

import java.util.ArrayList;

public class IncreaseBudget extends Command {
    public IncreaseBudget() {
        limitParameter = 1;
        parameters = new ArrayList<>();
    }

    public String execute(User user, String message) {
        var operationResult = user.increaseMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы увеличили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return ServiceFunctions.generateCommandParameterError();
    }
}
