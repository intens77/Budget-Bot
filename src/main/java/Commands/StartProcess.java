package Commands;

import Patterns.Command;
import WorkingClasses.ServiceFunctions;
import WorkingClasses.User;

import java.util.ArrayList;

public class StartProcess extends Command {
    public StartProcess() {
        limitParameter = 0;
        parameters = new ArrayList<>();
    }

    @Override
    public String execute(User user, String message) {
        return ServiceFunctions.readFileContent("start.txt");
    }
}
