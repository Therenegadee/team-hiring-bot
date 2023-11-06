package rassvet.team.hire.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rassvet.team.hire.bot.handler.UpdateHandler;

@Getter
@Component
@PropertySource("classpath:application.yml")
public class RassvetBot extends TelegramLongPollingBot {
    @Value("${telegram.username}")
    private String botUsername;
    @Value("${telegram.token}")
    private String botToken;
    private final UpdateHandler updateHandler;

    public RassvetBot(
            TelegramBotsApi telegramBotsApi,
            String botUsername,
            String botToken,
            UpdateHandler updateHandler
    ) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.updateHandler = updateHandler;
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage responseMsg = updateHandler.handleUpdate(update);
        sendResponse(responseMsg);
    }

    private void sendResponse(SendMessage responseMsg) {
        try {
            execute(responseMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
