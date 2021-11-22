package WorkingClasses;

import Commands.*;
import Patterns.*;


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
            commandsQueue.add(new CommandCall(users.get(userId), usersCommands.get(message)));
            return "Сумма:";
//            users.get(userId).setLastUserCommand(usersCommands.get(message));
//            return "Введите сумму или параметры:";

//        } else if (users.get(userId).getLastUserCommand() == usersCommands.get("/decrease_budget")){
//            users.get(userId).setLastCategory(message);
//            return String.format("Введите сумму для категории %s", message);}
        }else {
            var lastUserCommand = users.get(userId).getLastUserCommand();
//            if (lastUserCommand == usersCommands.get("/decrease_budget"))
//                return usersCommands.get("/add_category").execute(users.get(userId), message);
            if (lastUserCommand != null){
                users.get(userId).setLastUserCommand(null);
                return lastUserCommand.execute(users.get(userId), message);
            }
            return ServiceFunctions.generateCommandError();
        }
    }

    public HashMap<String, User> getUsers(){
        return users;
    }
}
