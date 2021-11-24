package Commands;

import Patterns.Command;
import WorkingClasses.User;

public class AddCategory extends Command {
    public AddCategory(Integer limitParameter) {
        super(limitParameter);
    }

    @Override
    public String execute(User user, String message) {
        if (user.containsCategory(message))
            return "Категория уже существует";
        user.addCategory(message);
        return "Добавлена категория " + message;
    }
}
