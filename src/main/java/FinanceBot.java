import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class FinanceBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            StringBuilder helpMessage = new StringBuilder();
            if (update.getMessage().getText().equalsIgnoreCase("help")) {
                try(BufferedReader reader = new BufferedReader(new FileReader("/home/vladislav/Рабочий стол/TelegramBot/src/main/resources/help.txt")))
                {
                    String line;
                    while((line = reader.readLine()) != null){
                        helpMessage.append(line);
                        helpMessage.append("\n\n");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(helpMessage.toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {

        return null;
    }
    @Override
    public String getBotToken() {

        return null;
    }
}

