import java.util.HashMap;
import java.util.Map;


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
        systemCommands.put("/check_stat", this::checkStatistic);
        usersCommands.put("/increase_budget", this::increaseBudget);
        usersCommands.put("/decrease_budget", this::decreaseBudget);
        usersCommands.put("/set_budget", this::setBudget);
        usersCommands.put("/reset_budget", this::resetBudget);
        usersCommands.put("/add_category", this::addCategory);
    }
    public HashMap<String, User> getUsers() {
        return users;
    }

    public String processUserMessage(String userId, String message) {
        if (!users.containsKey(userId))
            users.put(userId, new User(userId));
        if ((message != null) & (systemCommands.containsKey(message))) {
            return systemCommands.get(message).execute(users.get(userId), message);
        } else if ((message != null) & (usersCommands.containsKey(message))) {
            users.get(userId).push(usersCommands.get(message));
            return "Введите сумму или параметры:";
        } else {
            try {
                var lastUserCommand = users.get(userId).pop();
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
        var operationResult = user.decreaseWithCategory(message);
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

    public String addCategory(User user, String message){
        user.addCategory(message);
        return "Вы добавили новую категорию расходов " + message;
    }

    public String checkStatistic(User user, String message){
        StringBuilder result = new StringBuilder();
        var sum = 0F;
        result.append("Ваши расходы по категориям:\n");
        for (Map.Entry<String, Float> entry : user.getCategories().entrySet())
        {
            String key = entry.getKey();
            Float value = entry.getValue();
            sum += value;
            result.append(key).append(": ").append(value).append("\n");
        }
        result.append("Всего вы потратили: ").append(sum);
        return result.toString();
    }
}
