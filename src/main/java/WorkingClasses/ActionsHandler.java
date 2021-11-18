package WorkingClasses;

import Commands.*;
import Patterns.*;

import java.util.HashMap;


public class ActionsHandler {

    private final HashMap<String, Command> systemCommands;
    private final HashMap<String, Command> usersCommands;
    private final HashMap<String, User> users;

    public ActionsHandler() {
        systemCommands = new HashMap<>();
        usersCommands = new HashMap<>();
        users = new HashMap<>();
        systemCommands.put("/start", new StartProcess());
        systemCommands.put("/get_strategies", new GetStrategies());
        systemCommands.put("/check_budget", new CheckBudget());
        usersCommands.put("/increase_budget", new IncreaseBudget());
        usersCommands.put("/decrease_budget", new DecreaseBudget());
        usersCommands.put("/set_budget", new SetBudget());
        usersCommands.put("/reset_budget", new ResetBudget());
    }

    public String processUserMessage(String userId, String message) {
        if (!users.containsKey(userId))
            users.put(userId, new User(userId));
        if ((message != null) & (systemCommands.containsKey(message))) {
            return systemCommands.get(message).execute(users.get(userId), message);
        } else if ((message != null) & (usersCommands.containsKey(message))) {
            users.get(userId).saveLastUserCommand(usersCommands.get(message));
            return "Сумма:";
        } else {
            var lastUserCommand = users.get(userId).getLastUserCommand();
            if (lastUserCommand != null)
                return lastUserCommand.execute(users.get(userId), message);
            return ServiceFunctions.generateCommandError();
        }
    }
}
