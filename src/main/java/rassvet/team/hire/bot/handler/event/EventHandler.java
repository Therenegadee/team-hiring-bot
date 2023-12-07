package rassvet.team.hire.bot.handler.event;

import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;

public interface EventHandler {
    void handleEvent(Update update, BotState botState);
}
