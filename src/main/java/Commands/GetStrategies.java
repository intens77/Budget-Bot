package Commands;

import Patterns.Command;
import WorkingClasses.User;

public class GetStrategies extends Command {
    public GetStrategies(Integer limitParameter) {
        super(limitParameter);
    }

    @Override
    public String execute(User user, String message) {
        return "Стратегия 1\n Стратегия 2\n Стратегия 3";
    }
}
