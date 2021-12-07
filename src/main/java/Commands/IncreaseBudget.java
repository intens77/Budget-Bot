package Commands;

import Objects.User;
import Patterns.Command;
import WorkingClasses.EntityManager;
import WorkingClasses.ServiceFunctions;

import java.util.ArrayList;

public class IncreaseBudget extends Command {
    public IncreaseBudget() {
        parameters = new ArrayList<>();
        limitParameter = 1;
        outMessage = "Введите сумму";
    }

    public String execute(User user, String message) {
        boolean operationResult = user.increaseMonthBudget(Float.parseFloat(message));
        if (operationResult) {
            EntityManager.updateUser(user);
            return String.format("Отлично, Вы увеличили ваш " + "ежемесячный бюджет. Он составляет %s рублей", user.checkMonthBudget());
        }
        return ServiceFunctions.generateCommandParameterError();
    }
}
