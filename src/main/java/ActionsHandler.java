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
        usersCommands.put("/increase_budget", this::increaseBudget);
        usersCommands.put("/decrease_budget", this::decreaseBudget);
        usersCommands.put("/set_budget", this::setBudget);
        usersCommands.put("/reset_budget", this::resetBudget);
    }

    public String processUserMessage(String userId, String message) {
        if (!users.containsKey(userId))
            users.put(userId, new User(userId));
        if ((message != null) & (systemCommands.containsKey(message))) {
            return systemCommands.get(message).execute(userId, message);
        } else if ((message != null) & (usersCommands.containsKey(message))) {
            users.get(userId).push(usersCommands.get(message));
            return "Сумма:";
        }
        try {
            var lasUserCommand = users.get(userId).pop();
            return lasUserCommand.execute(userId, message);
        } catch (Exception e) {
            return generateCommandError();
        }
    }

    public String startProcess(String userId, String message) {
        return SecondaryFunctions.readFileContent("start.txt");
    }

    public String getStrategies(String userId, String message) {
        return "Стратегия 1\n Стратегия 2\n Стратегия 3";
    }

    public String setBudget(String userId, String message) {
        var currentUser = users.get(userId);
        var operationResult = currentUser.setMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы установили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    currentUser.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String resetBudget(String userId, String message) {
        var currentUser = users.get(userId);
        var operationResult = currentUser.resetMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы переустановили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    currentUser.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String increaseBudget(String userId, String message) {
        var currentUser = users.get(userId);
        var operationResult = currentUser.increaseMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы увеличили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    currentUser.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String decreaseBudget(String userId, String message) {
        var currentUser = users.get(userId);
        var operationResult = currentUser.decreaseMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы уменьшили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    currentUser.checkMonthBudget());
        return generateCommandParameterError();
    }

    public String generateCommandParameterError() {
        return "Произошла ошибка. Скорее всего, " +
                "Вы передали неверный параметр. Пожалуйста, прочитайте инструкцию и попробуйте снова";
    }

    public String generateCommandError() {
        return "Я не знаю такую команду:(";
    }
}
