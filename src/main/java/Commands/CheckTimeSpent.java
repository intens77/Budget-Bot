package Commands;

import Objects.Category;
import Objects.DateSpent;
import Objects.User;
import Patterns.Command;
import org.glassfish.grizzly.utils.Pair;

import java.sql.Date;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CheckTimeSpent extends Command {

    private final HashMap<String, Month> months;
    public final static String[] timeIntervals = {"Сегодня", "Вчера", "Текущий месяц", "Неделя", "Все время"};
    public Pattern monthWithYear;
    public Pattern exactDay;
    public CheckTimeSpent() {
        months = new HashMap<>() {};
        months.put("Январь", Month.JANUARY);
        months.put("Февраль", Month.FEBRUARY);
        months.put("Март", Month.MARCH);
        months.put("Апрель", Month.APRIL);
        months.put("Май", Month.MAY);
        months.put("Июнь", Month.JUNE);
        months.put("Июль", Month.JULY);
        months.put("Август", Month.AUGUST);
        months.put("Сентябрь", Month.SEPTEMBER);
        months.put("Октябрь", Month.OCTOBER);
        months.put("Ноябрь", Month.AUGUST);
        months.put("Декабрь", Month.DECEMBER);
        monthWithYear = Pattern.compile("[А-Яа-я]* \\d{4}");
        exactDay = Pattern.compile("\\d{4}[-. ]\\d{2}[-. ]\\d{2}");
        parameters = new ArrayList<>();
        limitParameter = 1;
        outMessage = "Выберите нужный промежуток\nили введите промежуток в формате год-месяц-день\n" +
                "или месяц и год";
    }

    @Override
    public String execute(User user, String message) {
        var categories = user.getCategories();
        StringBuilder statisticMessage = new StringBuilder();
        for (Category category: categories){
            try {
                Float sum = parse(message, category);
            statisticMessage.append(category.name).append(": ").append(parse(message, category)).append("\n");
            } catch (Exception e) {
                return "Вы вели некорректное сообщение";
            }
        }
        return statisticMessage.toString();
    }

    public float parse(String message, Category category){
        Predicate<? super DateSpent> predicate = null;
        if (exactDay.matcher(message).find()){
            var finalMessage = Arrays.stream(message.split("[-. ]")).reduce((x, y) -> x + "-" + y).get();
            predicate = (DateSpent x) -> x.getDate().equals(Date.valueOf(finalMessage));
        }
        else if (monthWithYear.matcher(message).find()){
            var time = message.split(" ");
            var month = months.get(time[0]);
            var year = time[1];
            Date begin  = Date.valueOf(String.format("%s-%s-01", year, month.getValue()));
            Date end = Date.valueOf(String.format("%s-%s-%s", year, month.getValue(), month.length(Year.now().isLeap())));
            predicate = (DateSpent x) -> x.getDate().after(begin) && x.getDate().before(end);
        }
        else
            switch (message) {
                case "Сегодня":
                    var curTime = Date.valueOf(LocalDate.now());
                    predicate = (DateSpent x) -> x.getDate().equals(curTime);
                    break;
                case "Вчера":
                    var tommorrow = Date.valueOf(LocalDate.now().minusDays(1));
                    predicate = (DateSpent x) -> x.getDate().equals(tommorrow);
                    break;
                case "Текущий месяц":
                    var dayMonthAgo = Date.valueOf(LocalDate.now().minusMonths(1));
                    predicate = (DateSpent x) -> x.getDate().after(dayMonthAgo);
                    break;
                case "Неделя":
                    var dayWeekAgo = Date.valueOf(LocalDate.now().minusWeeks(1));
                    predicate = (DateSpent x) -> x.getDate().after(dayWeekAgo);
                    break;
                case "Все время":
                    return category.getAmountSpent();
            }
        var sum =  category.getDateSpentList().stream().filter(predicate).map(DateSpent::getSpent).reduce(Float::sum);
        if (sum.isPresent())
            return sum.get();
        else return 0;
    }
}
