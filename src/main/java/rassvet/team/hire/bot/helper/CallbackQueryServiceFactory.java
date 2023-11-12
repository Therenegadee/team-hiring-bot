package rassvet.team.hire.bot.helper;

import rassvet.team.hire.bot.service.interfaces.CallbackQueryService;

public interface CallbackQueryServiceFactory {
    CallbackQueryService createService(String callbackData);
}
