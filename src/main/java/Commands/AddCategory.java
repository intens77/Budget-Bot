package Commands;

import Patterns.Command;
import Objects.User;

public class AddCategory extends Command {
    private static final int limitParameter = 1;

    @Override
    public String execute(User user, String message) {
        if (user.containsCategory(message))
            return "Категория уже существует";
        user.addCategory(message);
        return "Добавлена категория " + message;
    }
}
