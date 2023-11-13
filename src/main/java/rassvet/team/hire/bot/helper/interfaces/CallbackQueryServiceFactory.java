package rassvet.team.hire.bot.helper.interfaces;

import rassvet.team.hire.bot.service.interfaces.CallbackQueryService;

public interface CallbackQueryServiceFactory {
    CallbackQueryService createService(String callbackData);
}
