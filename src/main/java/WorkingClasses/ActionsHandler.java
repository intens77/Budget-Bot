package WorkingClasses;

import Commands.*;
import Patterns.Command;

import java.util.HashMap;
import java.util.LinkedList;


public class ActionsHandler {

    private final HashMap<String, Command> systemCommands;
    private final HashMap<String, Command> usersCommands;
    private final HashMap<String, User> users;
    private final LinkedList<CommandCall> commandsQueue;

    public ActionsHandler() {
        systemCommands = new HashMap<>();
        usersCommands = new HashMap<>();
        users = new HashMap<>();
        commandsQueue = new LinkedList<>();
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
            commandsQueue.add(new CommandCall(users.get(userId), usersCommands.get(message)));
            return "Сумма:";
        } else {
            if (commandsQueue.isEmpty()) return ServiceFunctions.generateCommandError();
            CommandCall processingCommandCall = commandsQueue.remove();
            return processingCommandCall.handle(message);
        }
    }
}
