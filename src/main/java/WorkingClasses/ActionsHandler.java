package WorkingClasses;

import Commands.*;
import Patterns.*;


import java.util.ArrayList;
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
        systemCommands.put("/start", new StartProcess(0));
        systemCommands.put("/get_strategies", new GetStrategies(0));
        systemCommands.put("/check_budget", new CheckBudget(0));
        usersCommands.put("/increase_budget", new IncreaseBudget(1));
        usersCommands.put("/decrease_budget", new DecreaseBudget(2));
        usersCommands.put("/set_budget", new SetBudget(1));
        usersCommands.put("/reset_budget", new ResetBudget(1));
        systemCommands.put("/check_stat", new CheckStatistic(0));
        usersCommands.put("/add_category", new AddCategory(1));
    }

    public String processUserMessage(String userId, String message) {
        if (!users.containsKey(userId))
            users.put(userId, new User(userId));
        if ((message != null) & (systemCommands.containsKey(message))) {
            return systemCommands.get(message).execute(users.get(userId), message);
        } else if ((message != null) & (usersCommands.containsKey(message))) {
            usersCommands.get(message).getParameters().clear();
            usersCommandsCalls.put(userId, usersCommands.get(message));
            return "Введите параметры:";
        }else {
            if (usersCommandsCalls.isEmpty()) return ServiceFunctions.generateCommandError();
            usersCommandsCalls.get(userId).getParameters().add(message);
            if (!usersCommandsCalls.get(userId).isEnough())
                return "Впишите дополнительные параметры";
            Command processingCommandCall = usersCommandsCalls.get(userId);
            usersCommandsCalls.remove(userId);
            return processingCommandCall.execute(users.get(userId),
                    String.join(", ",processingCommandCall.getParameters()));
        }
    }

    public HashMap<String, User> getUsers(){
        return users;
    }

    public ArrayList<String> getCommand() {
        var strCommands = new ArrayList<>(systemCommands.keySet());
        strCommands.addAll(usersCommands.keySet());
        return strCommands;
    }
}
