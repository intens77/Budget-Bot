package Tests;

import Bot.FinanceBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestsBot {
    FinanceBot bot;

    @BeforeEach
    void setUp() {
        bot = new FinanceBot();
    }

    @Test
    void checkHelp() {
        assertEquals("Это бот для распределения вашего бюджета \n" +
                "Здесь вы можете написать свой бюджет на ближайший месяц,\n" +
                "а наш бот поможет Вам выбрать оптимальную стратегию для расходов",
                bot.commands.get("/help").myFunction());
    }

//    @Test
//    void checkBudget()
//    {
//        assertEquals("Вы должны тратить в день по 10000", bot.commands.get("/budget").myFunction());
//    }

    @Test
    void checkEnd()
    {
        bot.commands.get("/exit").myFunction();
        assertFalse(bot.isWork);
    }

    @Test
    void checkEndMessage()
    {
        assertEquals("Всего хорошего и спасибо за рыбу", bot.commands.get("/exit").myFunction());
    }
}