package WorkingClasses;

import Commands.*;
import Objects.User;
import Patterns.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        systemCommands.put("старт", StartProcess::new);
        systemCommands.put("Посмотреть стратегии", GetStrategies::new);
        systemCommands.put("Узнать бюджет", CheckBudget::new);
        usersCommands.put("Увеличить бюджет", IncreaseBudget::new);
        usersCommands.put("Ввести расходы", DecreaseBudget::new);
        usersCommands.put("Установить бюджет", SetBudget::new);
        usersCommands.put("Переустановить бюджет", ResetBudget::new);
        systemCommands.put("Узнать статистику", CheckStatistic::new);
        usersCommands.put("Добавить категорию расходов", AddCategory::new);
        usersCommands.put("Проверить расходы", CheckTimeSpent::new);
    }

    public String processUserMessage(String userId, String message) {
        if (users.isEmpty()) loadDataFromDatabase();
        if (!users.containsKey(userId)) {
            User newUser = new User(userId);
            users.put(userId, newUser);
            EntityManager.saveUser(newUser);
        }
        if ((message != null) && (systemCommands.containsKey(message))) {
            return systemCommands.get(message).get().execute(users.get(userId), message);
        } else if ((message != null) && (usersCommands.containsKey(message))) {
            usersCommandsCalls.put(userId, usersCommands.get(message).get());
            return usersCommands.get(message).get().getOutMessage();
//            return "Введите параметры:";
        } else {
            if (usersCommandsCalls.get(userId) == null) return ServiceFunctions.generateCommandError();
            usersCommandsCalls.get(userId).addParameter(message);
            if (!usersCommandsCalls.get(userId).isEnough()) return "Впишите дополнительные параметры";
            Command processingCommandCall = usersCommandsCalls.get(userId);
            usersCommandsCalls.remove(userId);
            return processingCommandCall.execute(users.get(userId), String.join(", ", processingCommandCall.getParameters()));
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public ArrayList<String> getCommand() {
        var strCommands = new ArrayList<>(systemCommands.keySet());
        strCommands.addAll(usersCommands.keySet());
        return strCommands;
    }

    private void loadDataFromDatabase() {
        List<User> rememberedUsers = EntityManager.findAllUsers();
        for (User user : rememberedUsers) {
            users.put(user.getTelegramId(), user);
        }
    }
}
