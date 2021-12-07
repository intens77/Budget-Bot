package Commands;

import Objects.Category;
import Objects.User;
import Patterns.Command;

import java.util.ArrayList;

public class AddCategory extends Command {
    public AddCategory() {
        parameters = new ArrayList<>();
        limitParameter = 1;
        outMessage = "Введите название категории";
    }

    @Override
    public String execute(User user, String message) {
        if (user.containsCategory(message)) return "Категория уже существует";
        user.addCategory(new Category(message, 0));
        return "Добавлена категория " + message;
    }
}
