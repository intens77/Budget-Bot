package Commands;

import Patterns.Command;
import Objects.User;


public class GetStrategies extends Command {
    private static final int limitParameter = 0;

    @Override
    public String execute(User user, String message) {
        return "Стратегия 1\n Стратегия 2\n Стратегия 3";
    }
}
