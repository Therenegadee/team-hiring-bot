package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;

public interface ApplicationService {
    void processNameInput(Update update, BotState botState);
    void processAgeInput(Update update, BotState botState);
    void processPhoneNumberInput(Update update, BotState botState);
    void processContactMethodInput(Update update, BotState botState);
    void processExperienceInput(Update update, BotState botState);
}
