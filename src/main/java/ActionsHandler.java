import java.util.HashMap;


public class ActionsHandler {

    private final HashMap<String, ICommand> commands;
    private final HashMap<String, User> users;

    public ActionsHandler() {
        commands = new HashMap<>();
        commands.put("/start", this::startProcess);
        commands.put("/get_strategies", this::getStrategies);
        commands.put("/increase_budget", this::increaseBudget);
        commands.put("/decrease_budget", this::decreaseBudget);
        commands.put("/set_budget", this::setBudget);
        commands.put("/reset_budget", this::resetBudget);
        users = new HashMap<>();
    }

    public String processUserMessage(String userId, String message) {
        if (!users.containsKey(userId))
            users.put(userId, new User(userId));
        if ((message != null) & (commands.containsKey(message))) {
            return commands.get(message).execute(userId, message);
        }
        return generateError();
    }

    public String startProcess(String userId, String message) {
        return SecondaryFunctions.readFileContent("start.txt");
    }

    public String generateError() {
        return "Я не знаю такую команду:(";
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
        return "Произошла ошибка. Скорее всего, " +
                "Вы передали неверный параметр. Пожалуйста, прочитайте инструкцию и попробуйте снова";
    }

    public String resetBudget(String userId, String message) {
        var currentUser = users.get(userId);
        var operationResult = currentUser.resetMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы переустановили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    currentUser.checkMonthBudget());
        return "Произошла Ошибка. Скорее всего, " +
                "Вы передали неверный параметр. Пожалуйста, прочитайте инструкцию и попробуйте снова";
    }

    public String increaseBudget(String userId, String message) {
        var currentUser = users.get(userId);
        var operationResult = currentUser.increaseMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы увеличили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    currentUser.checkMonthBudget());
        return "Произошла ошибка. Скорее всего, " +
                "Вы передали неверный параметр. Пожалуйста, прочитайте инструкцию и попробуйте снова";
    }

    public String decreaseBudget(String userId, String message) {
        var currentUser = users.get(userId);
        var operationResult = currentUser.decreaseMonthBudget(Float.parseFloat(message));
        if (operationResult)
            return String.format("Отлично, Вы уменьшили ваш " +
                            "ежемесячный бюджет. Он составляет %s рублей",
                    currentUser.checkMonthBudget());
        return "Произошла ошибка. Скорее всего, " +
                "Вы передали неверный параметр. Пожалуйста, прочитайте инструкцию и попробуйте снова";
    }
}
