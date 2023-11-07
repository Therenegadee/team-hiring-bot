package rassvet.team.hire.bot.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotState;

@Service
public interface Command {
    void handleCommand(Update update, BotState botState);
    void handleTextInput(Update update, BotState botState);
}
