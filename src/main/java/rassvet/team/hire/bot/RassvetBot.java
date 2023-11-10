package rassvet.team.hire.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rassvet.team.hire.bot.handler.UpdateHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
@Getter
public class RassvetBot extends TelegramLongPollingBot {
    @Value("${telegram.username}")
    private String botUsername;
    @Value("${telegram.token}")
    private String botToken;
    private final UpdateHandler updateHandler;

    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handleUpdate(update);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        onUpdateReceived(updates.get(updates.size()-1));
    }

}
