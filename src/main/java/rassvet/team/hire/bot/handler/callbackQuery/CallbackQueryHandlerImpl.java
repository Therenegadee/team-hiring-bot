package rassvet.team.hire.bot.handler.callbackQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.exceptions.InternalException;
import rassvet.team.hire.bot.helper.interfaces.CallbackQueryServiceFactory;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandlerImpl implements CallbackQueryHandler {
    private final CallbackQueryServiceFactory callbackQueryServiceFactory;

    @Override
    public void handleCallbackQuery(Update update, String callbackData) {
        CallbackQueryHandler service = callbackQueryServiceFactory.createService(callbackData);
        if (service != null) {
            service.handleCallbackQuery(update, callbackData);
        } else {
            throw new InternalException(update);
        }
    }
}
