package WorkingClasses;

import Commands.*;
import Objects.User;
import Patterns.Command;

import java.util.HashMap;


public class ActionsHandler {

    private final HashMap<String, Command> systemCommands;
    private final HashMap<String, Command> usersCommands;
    private final HashMap<String, User> users;
    private final HashMap<String, Command> usersCommandsCalls;

    public ActionsHandler() {
        systemCommands = new HashMap<>();
        usersCommands = new HashMap<>();
        users = new HashMap<>();
        usersCommandsCalls = new HashMap<>();
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
            usersCommandsCalls.put(userId, usersCommands.get(message));
            return "Сумма:";
        } else {
            if (usersCommandsCalls.isEmpty()) return ServiceFunctions.generateCommandError();
            Command processingCommandCall = usersCommandsCalls.get(userId);
            return processingCommandCall.execute(users.get(userId), message);
        }
    }
}
