import java.util.HashMap;


public class ActionsHandler {

    private final HashMap<String, ICommand> systemCommands;
    private final HashMap<String, ICommand> usersCommands;
    private final HashMap<String, User> users;

    public ActionsHandler() {
        systemCommands = new HashMap<>();
        usersCommands = new HashMap<>();
        users = new HashMap<>();
        systemCommands.put("/start", this::startProcess);
        systemCommands.put("/get_strategies", this::getStrategies);
        systemCommands.put("/check_budget", this::checkBudget);
        usersCommands.put("/increase_budget", this::increaseBudget);
        usersCommands.put("/decrease_budget", this::decreaseBudget);
        usersCommands.put("/set_budget", this::setBudget);
        usersCommands.put("/reset_budget", this::resetBudget);
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
            try {
                var lastUserCommand = users.get(userId).getLastUserCommand();
                return lastUserCommand.execute(users.get(userId), message);
            } catch (Exception e) {
                return generateCommandError();
            }
        }
    }

    public String startProcess(User user, String message) {
        return SecondaryFunctions.readFileContent("start.txt");
    }

    public String getStrategies(User user, String message) {
        return "Стратегия 1\n Стратегия 2\n Стратегия 3";
    }

    public String setBudget(User user, String message) {
        var operationResult = user.setMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы установили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String resetBudget(User user, String message) {
        var operationResult = user.resetMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы переустановили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String increaseBudget(User user, String message) {
        var operationResult = user.increaseMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы увеличили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String decreaseBudget(User user, String message) {
        var operationResult = user.decreaseMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы уменьшили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    user.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String checkBudget(User user, String message) {
        return String.format("Ваш текущий бюджет: %s", user.checkMonthBudget());
    }

    public static String generateCommandParameterError() {
        return "Произошла ошибка. Скорее всего, " +
                "Вы передали неверный параметр. Пожалуйста, прочитайте инструкцию и попробуйте снова";
    }

    public static String generateCommandError() {
        return "Я не знаю такую команду:(";
    }
}
