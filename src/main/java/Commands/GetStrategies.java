package Commands;

import Objects.User;
import Patterns.Command;

import java.util.ArrayList;

public class GetStrategies extends Command {
    public GetStrategies() {
        parameters = new ArrayList<>();
        limitParameter = 0;
    }

    @Override
    public String execute(User user, String message) {
        return "Стратегия 1\n Стратегия 2\n Стратегия 3";
    }
}
