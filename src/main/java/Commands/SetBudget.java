package Commands;

import Objects.User;
import Patterns.Command;
import WorkingClasses.EntityManager;
import WorkingClasses.ServiceFunctions;

import java.util.ArrayList;

public class SetBudget extends Command {
    public SetBudget() {
        parameters = new ArrayList<>();
        limitParameter = 1;
    }

    @Override
    public String execute(User user, String message) {
        var operationResult = user.setMonthBudget(Float.parseFloat(message));
        if (operationResult) {
            EntityManager.updateUser(user);
            return String.format("Отлично, Вы установили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        }
        return ServiceFunctions.generateCommandParameterError();
    }
}
