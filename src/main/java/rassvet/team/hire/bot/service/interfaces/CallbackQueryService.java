package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryService {
    void handleCallbackQuery(Update update, String callbackData);
}
