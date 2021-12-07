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
    ReplyKeyboardMarkup replyKeyboardMarkup;
    ReplyKeyboardMarkup categoriesKeyboard;

    public BudgetBot() {
        messageSender = new SendMessage();
        actionsHandler = new ActionsHandler();
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        categoriesKeyboard = new ReplyKeyboardMarkup();
        setCommand(replyKeyboardMarkup, actionsHandler.getCommand());
        messageSender.setReplyMarkup(replyKeyboardMarkup);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String userId = update.getMessage().getChatId().toString();
            messageSender.setChatId(userId);
            messageSender.setText(actionsHandler.processUserMessage(userId, update.getMessage().getText()));
            if (update.getMessage().getText().equals("Ввести расходы")) {
                setCommand(categoriesKeyboard, actionsHandler.getUsers().get(userId).getCategoriesName());
                messageSender.setReplyMarkup(categoriesKeyboard);
            } else messageSender.setReplyMarkup(replyKeyboardMarkup);

            try {
                execute(messageSender);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCommand(ReplyKeyboardMarkup replyKeyboardMarkup, ArrayList<String> strArray) {
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        for (String str : strArray) {
            KeyboardRow row = new KeyboardRow();
            row.add(str);
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