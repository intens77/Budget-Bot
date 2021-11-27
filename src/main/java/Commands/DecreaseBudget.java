package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import Objects.User;

public class DecreaseBudget extends Command {
    private static final int limitParameter = 2;

    public String execute(User user, String message) {
        var operationResult = user.decreaseWithCategory(message);
        if (operationResult)
            return String.format("Отлично, Вы уменьшили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return ServiceFunctions.generateCommandParameterError();
    }
}
