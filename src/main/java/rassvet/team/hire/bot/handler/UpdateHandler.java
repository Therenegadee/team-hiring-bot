package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.BotState;
import rassvet.team.hire.bot.commands.Command;
import rassvet.team.hire.bot.commands.CommandSetter;

import java.util.Objects;

import static rassvet.team.hire.bot.utils.Consts.CANT_UNDERSTAND;
import static rassvet.team.hire.bot.utils.Consts.INTERNAL_ERROR;

@Service
@RequiredArgsConstructor
public class UpdateHandler {
    private final CommandSetter commandSetter;
    private final BotCache botCache;
    private final SendMessage sendMessage;

    public SendMessage handleUpdate(Update update){
        Long telegramId = update.getMessage().getFrom().getId();
        BotState botState = botCache.getBotState(telegramId);
        if(botState.equals(BotState.BASIC_STATE) && !update.getMessage().getText().startsWith("/")) {
            return textRequestError(update);
        }
        Command command = commandSetter.setCommand(botState);
        if(Objects.isNull(command)) {
            return internalError(update);
        } else {
            return command.handle(update);
        }
    }

    private SendMessage internalError(Update update){
        String chatId = update.getMessage().getChatId().toString();
        return SendMessage.builder()
                .chatId(chatId)
                .text(INTERNAL_ERROR)
                .build();
    }

    private SendMessage textRequestError(Update update){
        String chatId = update.getMessage().getChatId().toString();
        return SendMessage.builder()
                .chatId(chatId)
                .text(CANT_UNDERSTAND)
                .build();
    }


}
