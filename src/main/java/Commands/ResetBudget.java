package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import WorkingClasses.User;

import java.util.ArrayList;

public class ResetBudget extends Command {
    public ResetBudget() {
        limitParameter = 1;
        parameters = new ArrayList<>();
    }

    public String execute(User user, String message) {
        var operationResult = user.resetMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы переустановили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return ServiceFunctions.generateCommandParameterError();
    }
}
