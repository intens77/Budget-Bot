package Commands;

import Objects.Category;
import Objects.User;
import Patterns.Command;

import java.util.ArrayList;

public class CheckStatistic extends Command {
    public CheckStatistic() {
        parameters = new ArrayList<>();
        limitParameter = 0;
    }

    @Override
    public String execute(User user, String message) {
        StringBuilder result = new StringBuilder();
        float sum = 0F;
        result.append("Ваши расходы по категориям:\n");
        for (Category category : user.getCategories()) {
            sum += category.getAmountSpent();
            result.append(category).append("\n");
        }
        result.append("Всего вы потратили: ").append(sum);
        return result.toString();
    }
}
