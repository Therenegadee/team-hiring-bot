package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.exceptions.InternalException;
import rassvet.team.hire.bot.helper.CallbackQueryServiceFactory;
import rassvet.team.hire.bot.service.interfaces.CallbackQueryService;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandler {
    private final CallbackQueryServiceFactory callbackQueryServiceFactory;

    public void handle(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        CallbackQueryService service = callbackQueryServiceFactory.createService(callbackData);
        if (service != null) {
            service.handleCallbackQuery(update, callbackData);
        } else {
            throw new InternalException(update);
        }
    }
}
