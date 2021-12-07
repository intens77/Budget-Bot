package Commands;

import Objects.Category;
import Objects.DateSpent;
import Objects.User;
import Patterns.Command;

import java.sql.Date;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CheckTimeSpent extends Command {

    private HashMap<String, Month> months;
    public CheckTimeSpent() {
        months = new HashMap<>();
        months.put("Январь", Month.JANUARY);
        parameters = new ArrayList<>();
        limitParameter = 1;
    }

    @Override
    public String execute(User user, String message) {
        List<Category> categories = user.getCategories();
//        var curTime = parse(message);
        float allSum = 0.0F;
        for (Category category: categories){
            allSum += parse(message, category);
//            Predicate<? super DateSpent> predicate = (DateSpent x) -> x.getMonth().equals(curTime);
//            var sum =  category.getDateSpentList().stream().filter(predicate).collect(Collectors.toList());
//            for (var dateSpent: sum){
//                allSum += dateSpent.getSpent();
//            }
        }
        return "За " + message + " вы потратили " + allSum;
//        SimpleDateFormat format = new SimpleDateFormat();
//        format.applyPattern("yyyy-MM");
//        YearMonth currentMonth = YearMonth.parse(message, new DateTimeFormatterBuilder().toFormatter());
//        var optionalMonth =  user.getMonthSpent().stream().filter(x -> x.getMonth().equals(currentMonth)).findFirst();
//        if (optionalMonth.isPresent()) {
//            var currentM = optionalMonth.get();
//            return currentM.toString();
//        }
//        else
//            return "В этом месяце не было трат";
    }

    public float parse(String message, Category category){
//        var splitedmessage = message.split(" ");
//        var typeOfDate  = splitedmessage[0];
//        var mes = splitedmessage[1];
        Date curTime = Date.valueOf(LocalDate.now());
        Predicate<? super DateSpent> predicate = null;
        switch (message) {
            case "Сегодня":
                try {
                    predicate = (DateSpent x) -> x.getDate().equals(curTime);
//                    var sum = category.getDateSpentList().stream().filter(predicate).findFirst().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Месяц":
                try {
//                var month = Month.of(Integer.parseInt(splitedmessage[1]));
//                var length = month.length(Year.now().isLeap());
//                var begin = Date.valueOf("01." + message);
//                begin.toLocalDate().minusDays(7);
//                return Date.valueOf(String.valueOf(length) + message);
                    Date dayMonthAgo = Date.valueOf(LocalDate.now().minusMonths(1));
                    predicate = (DateSpent x) -> x.getDate().after(dayMonthAgo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Неделя":
                Date dayWeekAgo = Date.valueOf(LocalDate.now().minusWeeks(1));
                predicate = (DateSpent x) -> x.getDate().after(dayWeekAgo);
                break;
        }
        //else return null;
        Optional<Float> sum =  category.getDateSpentList().stream().filter(predicate)
                .map(DateSpent::getSpent).reduce(Float::sum);
        if (sum.isPresent())
            return sum.get();//.collect(Collectors.toList());
        else return 0;
    }
}
