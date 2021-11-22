package Commands;

import Patterns.Command;
import WorkingClasses.User;

public class AddCategory extends Command {
    public AddCategory(Integer limitParameter) {
        super(limitParameter);
    }

    @Override
    public String execute(User user, String message) {
        if (user.getCategories().containsKey(message))
            return "Категория " + message;
        user.addCategory(message);
        return "Вы добавили новую категорию расходов " + message;
    }
}
