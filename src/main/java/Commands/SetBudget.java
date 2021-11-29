package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import WorkingClasses.User;

import java.util.ArrayList;

public class SetBudget extends Command {
    public SetBudget() {
        limitParameter = 1;
        parameters = new ArrayList<>();
    }

    @Override
    public String execute(User user, String message) {
        var operationResult = user.setMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы установили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return ServiceFunctions.generateCommandParameterError();
    }
}
