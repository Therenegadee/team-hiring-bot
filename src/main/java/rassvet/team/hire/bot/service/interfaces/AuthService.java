package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;

public interface AuthService {

    void processSecretCodeInput(Update update, BotState botState);
}
