package WorkingClasses;

import Patterns.Command;

public class CommandCall {
    private final User user;
    private final Command command;

    public CommandCall(User user, Command command) {
        this.user = user;
        this.command = command;
    }

    public String handle(String message) {
        return command.execute(user, message);
    }
}
