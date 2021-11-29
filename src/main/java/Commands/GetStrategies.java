package Commands;

import Patterns.Command;
import WorkingClasses.User;

import java.util.ArrayList;

public class GetStrategies extends Command {
    public GetStrategies() {
        limitParameter = 0;
        parameters = new ArrayList<>();
    }

    @Override
    public String execute(User user, String message) {
        return "Стратегия 1\n Стратегия 2\n Стратегия 3";
    }
}
