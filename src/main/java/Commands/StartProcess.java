package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import Objects.User;


public class StartProcess extends Command {
    private static final int limitParameter = 0;

    @Override
    public String execute(User user, String message) {
        return ServiceFunctions.readFileContent("start.txt");
    }
}
