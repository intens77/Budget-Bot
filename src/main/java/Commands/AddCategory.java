package Commands;

import Patterns.Command;
import WorkingClasses.User;

import java.util.ArrayList;

public class AddCategory extends Command {
    public AddCategory() {
        limitParameter = 1;
        parameters = new ArrayList<>();
    }

    @Override
    public String execute(User user, String message) {
        if (user.containsCategory(message))
            return "Категория уже существует";
        user.addCategory(message);
        return "Добавлена категория " + message;
    }
}
