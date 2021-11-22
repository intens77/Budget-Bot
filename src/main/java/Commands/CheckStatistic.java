package Commands;

import Patterns.Command;
import WorkingClasses.User;

import java.util.Map;

public class CheckStatistic extends Command {
    public CheckStatistic(Integer limitParameter) {
        super(limitParameter);
    }

    @Override
    public String execute(User user, String message){
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
