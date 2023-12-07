package rassvet.team.hire.bot.helper.interfaces;

import rassvet.team.hire.bot.handler.callbackQuery.CallbackQueryHandler;

public interface CallbackQueryServiceFactory {
    CallbackQueryHandler createService(String callbackData);
}
