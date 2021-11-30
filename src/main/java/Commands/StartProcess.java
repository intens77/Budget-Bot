package Commands;

import Objects.User;
import Patterns.Command;
import WorkingClasses.ServiceFunctions;

import java.util.ArrayList;

public class StartProcess extends Command {
    public StartProcess() {
        parameters = new ArrayList<>();
        limitParameter = 0;
    }

    @Override
    public String execute(User user, String message) {
        return ServiceFunctions.readFileContent("start.txt");
    }
}
