package WorkingClasses;

import Commands.*;
import Objects.User;
import Patterns.Command;

import java.util.HashMap;
import java.util.List;


public class ActionsHandler {

    private final HashMap<String, Command> systemCommands;
    private final HashMap<String, Command> usersCommands;
    private final HashMap<String, User> users;
    private final HashMap<String, Command> usersCommandsCalls;
    private final UserService userService;

    public ActionsHandler() {
        systemCommands = new HashMap<>();
        usersCommands = new HashMap<>();
        users = new HashMap<>();
        usersCommandsCalls = new HashMap<>();
        userService = new UserService();
        systemCommands.put("/start", new StartProcess());
        systemCommands.put("/get_strategies", new GetStrategies());
        systemCommands.put("/check_budget", new CheckBudget());
        usersCommands.put("/increase_budget", new IncreaseBudget());
        usersCommands.put("/decrease_budget", new DecreaseBudget());
        usersCommands.put("/set_budget", new SetBudget());
        usersCommands.put("/reset_budget", new ResetBudget());
    }

    public String processUserMessage(String userId, String message) {
        if (users.isEmpty())
            loadDataFromDataBase();
        if (!users.containsKey(userId)) {
            User user = new User(userId);
            users.put(userId, user);
            userService.rememberUser(user);
        }
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

    private void loadDataFromDataBase() {
        List<User> rememberedUsers = userService.findAllUsers();
        for (User user : rememberedUsers) {
            users.put(user.getTelegramId(), user);
        }
    }
}
