package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import Objects.User;


public class SetBudget extends Command {
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
