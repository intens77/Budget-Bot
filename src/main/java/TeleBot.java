import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TeleBot {

    public static class FinanceBot extends TelegramLongPollingBot {
        private static HashMap<String, ICommand> commands;
        public FinanceBot() {
            commands = new HashMap<>();
            commands.put("/start", this::startProcess);
            commands.put("/getStrategies", this::getStrategies);
        }

        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                String botAnswer = processUserMessage(update.getMessage().getText());
                message.setText(botAnswer);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public String getBotUsername() {

            return "FinanceBot";
        }

        @Override
        public String getBotToken() {

            return "";
        }

        public static String processUserMessage(String message) {
            if (message != null) {
                if (commands.containsKey(message))
                    return commands.get(message).myFunction();
                return getException();
            }
            else return null;
        }
        public  String startProcess() {
            String globalMessage = "";
            try (BufferedReader reader = new BufferedReader(
                new FileReader("/home/vladislav/Рабочий стол/TelegramBot/src/main/resources/help.txt"))) {
                StringBuilder helpMessage = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    helpMessage.append(line);
                    helpMessage.append("\n\n");
                }
                globalMessage = helpMessage.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return globalMessage;
        }

        public static String getException(){
            return "Я не знаю такую команду:(";
        }
        public String getStrategies() {
            return "Стратегия 1\n Стратегия 2\n Стратегия 3";
        }
    }
}

