import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public final class BudgetBot extends TelegramLongPollingBot {

    private static SendMessage messageSender;
    private static ActionsHandler actionsHandler;

    public BudgetBot() {
        messageSender = new SendMessage();
        actionsHandler = new ActionsHandler();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            var userId = update.getMessage().getChatId().toString();
            messageSender.setChatId(userId);
            messageSender.setText(actionsHandler.processUserMessage(userId, update.getMessage().getText()));
            try {
                execute(messageSender);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {

        return "BudgetBot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_ACCESS_TOKEN");
    }
}


