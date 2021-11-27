package Commands;

import Objects.User;
import Patterns.Command;
import Objects.Category;

public class CheckStatistic extends Command {
    private static final int limitParameter = 0;

    @Override
    public String execute(User user, String message) {
        StringBuilder result = new StringBuilder();
        var sum = 0F;
        result.append("Ваши расходы по категориям:\n");
        for (Category category : user.getCategories()) {
            sum += category.getAmountSpent();
            result.append(category).append("\n");
        }
        result.append("Всего вы потратили: ").append(sum);
        return result.toString();
    }
}
