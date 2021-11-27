package Commands;

import Objects.User;
import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import WorkingClasses.UserService;


public class SetBudget extends Command {
    private static final int limitParameter = 1;

    @Override
    public String execute(User user, String message) {
        var operationResult = user.setMonthBudget(Float.parseFloat(message));
        if (operationResult) {
            UserService.saveOrUpdateUser(user);
            return String.format("Отлично, Вы установили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        }
        return ServiceFunctions.generateCommandParameterError();
    }
}
