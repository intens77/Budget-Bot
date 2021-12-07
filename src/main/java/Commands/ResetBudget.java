package Commands;

import Objects.User;
import Patterns.Command;
import WorkingClasses.EntityManager;
import WorkingClasses.ServiceFunctions;

import java.util.ArrayList;

public class ResetBudget extends Command {
    public ResetBudget() {
        parameters = new ArrayList<>();
        limitParameter = 1;
        outMessage = "Введите сумму";
    }

    public String execute(User user, String message) {
        boolean operationResult = user.resetMonthBudget(Float.parseFloat(message));
        if (operationResult) {
            EntityManager.updateUser(user);
            return String.format("Отлично, Вы переустановили ваш " + "ежемесячный бюджет. Он составляет %s рублей", user.checkMonthBudget());
        }
        return ServiceFunctions.generateCommandParameterError();
    }
}
