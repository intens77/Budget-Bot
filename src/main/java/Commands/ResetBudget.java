package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import Objects.User;


public class ResetBudget extends Command {
    private static final int limitParameter = 1;

    public String execute(User user, String message) {
        var operationResult = user.resetMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы переустановили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return ServiceFunctions.generateCommandParameterError();
    }
}
