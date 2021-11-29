package WorkingClasses;

import Commands.*;
import Patterns.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;


public class ActionsHandler {

    private final HashMap<String, Supplier<Command>> systemCommands;
    private final HashMap<String, Supplier<Command>> usersCommands;
    private final HashMap<String, User> users;
    private final HashMap<String, Command> usersCommandsCalls;

    public ActionsHandler() {
        systemCommands = new HashMap<>();
        usersCommands = new HashMap<>();
        users = new HashMap<>();
        usersCommandsCalls = new HashMap<>();
        systemCommands.put("/start", StartProcess::new);
        systemCommands.put("/get_strategies", GetStrategies::new);
        systemCommands.put("/check_budget", CheckBudget::new);
        usersCommands.put("/increase_budget", IncreaseBudget::new);
        usersCommands.put("/decrease_budget", DecreaseBudget::new);
        usersCommands.put("/set_budget", SetBudget::new);
        usersCommands.put("/reset_budget", ResetBudget::new);
        systemCommands.put("/check_stat", CheckStatistic::new);
        usersCommands.put("/add_category", AddCategory::new);
    }

    public String processUserMessage(String userId, String message) {
        if (!users.containsKey(userId))
            users.put(userId, new User(userId));
        if ((message != null) & (systemCommands.containsKey(message))) {
            return systemCommands.get(message).get().execute(users.get(userId), message);
        } else if ((message != null) & (usersCommands.containsKey(message))) {
            usersCommands.get(message).get().getParameters().clear();
            usersCommandsCalls.put(userId, usersCommands.get(message).get());
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
