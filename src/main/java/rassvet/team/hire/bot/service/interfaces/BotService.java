package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;

public interface BotService {
    void sendResponse(SendMessage responseMsg);
    int processBasicCases(Update update, BotState botState);
}
