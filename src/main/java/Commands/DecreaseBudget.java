package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import WorkingClasses.User;

public class DecreaseBudget extends Command {
    public DecreaseBudget(Integer limitParameter) {
        super(limitParameter);
    }

    public String execute(User user, String message) {
        var resultMessage = String.format("%s %s", user.getLastCategory(), message);
        var operationResult = user.decreaseWithCategory(resultMessage);
        if (operationResult)
            return String.format("Отлично, Вы уменьшили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return ServiceFunctions.generateCommandParameterError();
    }
}
