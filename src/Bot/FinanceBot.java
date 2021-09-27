package Bot;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;


public class FinanceBot {
    //String information = "Тут хранится описание бота";
    public HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

    public Boolean isWork;

    public FinanceBot()
    {
        isWork = true;
        commands.put("/help", () -> takeInformation());
        commands.put("/budget", () -> getBudget());
        commands.put("/exit", () -> endWork());

    }

    public String endWork()
    {
        isWork = false;
        return "Всего хорошего и спасибо за рыбу";
    }

    public String getBudget()
    {
        System.out.println("Введите суммы которой вы хотите распоряжаться:");
        Scanner scanner = new Scanner(System.in);
        Integer money = scanner.nextInt();
        return "Вы должны тратить в день по " + money;
    }

    public String takeInformation()
    {
        var information = "Это бот для распределения вашего бюджета " +
                "\n" +
                "Здесь вы можете написать свой бюджет на ближайший месяц," +
                "\n" +
                "а наш бот поможет Вам выбрать оптимальную стратегию для расходов";
        //if (Objects.equals(inputStr, "--help"))
        return information;
        //return "Данная команда не найдена";
    }
}

