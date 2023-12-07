package rassvet.team.hire.bot.handler.callbackQuery;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryHandler {
    void handleCallbackQuery(Update update, String callbackData);
}
