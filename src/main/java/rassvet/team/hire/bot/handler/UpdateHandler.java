package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.commands.Command;
import rassvet.team.hire.bot.commands.CommandSetter;
import rassvet.team.hire.bot.service.BotServiceImpl;

@Service
@RequiredArgsConstructor
public class UpdateHandler {
    private final CommandSetter commandSetter;
    private final BotCache botCache;
    private final BotServiceImpl botService;
    private final CallbackQueryHandlerImpl callbackQueryHandler;

    public void handleUpdate(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        BotState botState = botCache.getBotState(telegramId);
        if (botService.processBasicCases(update, botState) == 1) {
            return;
        }
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            callbackQueryHandler.handleCallbackQuery(update, callbackData);
            return;
        }
        Command command = commandSetter.setCommand(update);
        if (update.getMessage().getText().startsWith("/")) {
            command.handleCommand(update, botState);
        } else {
            command.handleTextInput(update, botState);
        }
    }
}
