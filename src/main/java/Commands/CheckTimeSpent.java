package Commands;

import Objects.Category;
import Objects.DateSpent;
import Objects.User;
import Patterns.Command;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CheckTimeSpent extends Command {

    public final static String[] timeIntervals = {"Сегодня", "Вчера", "Текущий месяц", "Неделя", "Все время"};
    private final HashMap<String, Month> months;
    public Pattern monthWithYear;
    public Pattern exactDay;

    public CheckTimeSpent() {
        months = new HashMap<>();
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
        List<Category> categories = user.getCategories();
        StringBuilder statisticMessage = new StringBuilder();
        for (Category category : categories) {
            try {
                Float sum = parse(message, category);
                statisticMessage.append(category.name).append(": ").append(parse(message, category)).append("\n");
            } catch (Exception e) {
                return "Вы вели некорректное сообщение";
            }
        }
        return statisticMessage.toString();
    }

    public float parse(String message, Category category) {
        Predicate<? super DateSpent> predicate = null;
        if (exactDay.matcher(message).find()) {
            String finalMessage = Arrays.stream(message.split("[-. ]")).reduce((x, y) -> x + "-" + y).get();
            predicate = (DateSpent x) -> x.getDate().equals(Date.valueOf(finalMessage));
        } else if (monthWithYear.matcher(message).find()) {
            String[] time = message.split(" ");
            Month month = months.get(time[0]);
            String year = time[1];
            Date begin = Date.valueOf(String.format("%s-%s-01", year, month.getValue()));
            Date end = Date.valueOf(String.format("%s-%s-%s", year, month.getValue(), month.length(Year.now().isLeap())));
            predicate = (DateSpent x) -> x.getDate().after(begin) && x.getDate().before(end);
        } else
            switch (message) {
                case "Сегодня":
                    Date curTime = Date.valueOf(LocalDate.now());
                    predicate = (DateSpent x) -> x.getDate().equals(curTime);
                    break;
                case "Вчера":
                    Date tomorrow = Date.valueOf(LocalDate.now().minusDays(1));
                    predicate = (DateSpent x) -> x.getDate().equals(tomorrow);
                    break;
                case "Текущий месяц":
                    Date dayMonthAgo = Date.valueOf(LocalDate.now().minusMonths(1));
                    predicate = (DateSpent x) -> x.getDate().after(dayMonthAgo);
                    break;
                case "Неделя":
                    Date dayWeekAgo = Date.valueOf(LocalDate.now().minusWeeks(1));
                    predicate = (DateSpent x) -> x.getDate().after(dayWeekAgo);
                    break;
                case "Все время":
                    return category.getAmountSpent();
            }
        Optional<Float> sum = category.getDateSpentList().stream().filter(predicate).map(DateSpent::getSpent).reduce(Float::sum);
        if (sum.isPresent())
            return sum.get();
        else return 0;
    }
}
