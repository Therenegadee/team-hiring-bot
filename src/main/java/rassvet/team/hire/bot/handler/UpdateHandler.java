package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.MisunderstandableInputException;
import rassvet.team.hire.bot.handler.callbackQuery.CallbackQueryHandlerImpl;
import rassvet.team.hire.bot.handler.event.EventHandlerSetter;
import rassvet.team.hire.bot.handler.commands.CommandHandler;
import rassvet.team.hire.bot.handler.event.EventHandler;

@Service
@RequiredArgsConstructor
public class UpdateHandler {
    private final BotCache botCache;
    private final CallbackQueryHandlerImpl callbackQueryHandler;
    private final EventHandlerSetter eventHandlerSetter;
    private final CommandHandler commandHandler;

    public void handleUpdate(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        BotState botState = botCache.getBotState(telegramId);
        if (update.getMessage().getText().startsWith("/")) {
            commandHandler.handleCommand(update);
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            callbackQueryHandler.handleCallbackQuery(update, callbackData);
        } else if ((botState.equals(BotState.ADMIN_STATE) || botState.equals(BotState.APPLICANT_STATE)) &&
                        !update.getMessage().getText().startsWith("/")
        ) {
            throw new MisunderstandableInputException(update);
        } else {
            EventHandler eventHandler = eventHandlerSetter.setEventHandler(update);
            eventHandler.handleEvent(update, botState);
        }
    }
}
