package Bot;

import java.util.Scanner;

public class Main {
    public static void main(String [] args)
    {
        var bot = new FinanceBot();
        Scanner input = new Scanner(System.in);
        String command = "";

        while (bot.isWork) {
            command = input.next();
            if (bot.commands.containsKey(command))
                System.out.println(bot.commands.get(command).myFunction());
            else
                System.out.println("Введите корректную команду");
                /*command = input.next();
                if (Objects.equals(command, "--help")) {
                    System.out.println(bot.takeInformation());
                } else if (Objects.equals(command, "/budget"))
                    System.out.println(bot.getBudget());
                else if (Objects.equals(command, "exit"))
                    bot.endWork();
                else
                    System.out.println("Введите корректную команду");*/
        }
    }
}