import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class BudgetBot extends TelegramLongPollingBot {

  private static SendMessage messageSender;
  private static ActionsHandler actionsHandler;
  public String Message;


  public BudgetBot() {
    messageSender = new SendMessage();
    actionsHandler = new ActionsHandler();
    Message = "";
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      String id =  update.getMessage().getChatId().toString();
      messageSender.setChatId(id);
      messageSender.setText(actionsHandler.processUserMessage(update.getMessage().getText(), id));
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


