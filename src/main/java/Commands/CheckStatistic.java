package Commands;

import Patterns.Command;
import WorkingClasses.Category;
import WorkingClasses.User;

public class CheckStatistic extends Command {
    public CheckStatistic(Integer limitParameter) {
        super(limitParameter);
    }

    @Override
    public String execute(User user, String message){
        StringBuilder result = new StringBuilder();
        var sum = 0F;
        result.append("Ваши расходы по категориям:\n");
        for (Category category : user.getCategories())
        {
            sum += category.getAmountSpent();
            result.append(category).append("\n");
        }
        result.append("Всего вы потратили: ").append(sum);
        return result.toString();
    }
}
