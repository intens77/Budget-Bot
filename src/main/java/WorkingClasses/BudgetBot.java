package WorkingClasses;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public final class BudgetBot extends TelegramLongPollingBot {

    private static SendMessage messageSender;
    private static ActionsHandler actionsHandler;
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

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
            if (update.getMessage().getText().equals("/decrease_budget")){
                setKeyboard(replyKeyboardMarkup, userId);
                messageSender.setReplyMarkup(replyKeyboardMarkup);
            }
            try {
                execute(messageSender);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void setKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup, String userId){
        var user = actionsHandler.getUsers().get(userId);
        var categories = user.getCategories().keySet();
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        for (String category: categories) {
            var row = new KeyboardRow();
            row.add(category);
            keyboardRows.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRows);
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


