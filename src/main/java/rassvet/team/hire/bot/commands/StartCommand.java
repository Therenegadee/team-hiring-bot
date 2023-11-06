package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.BotState;

import static rassvet.team.hire.bot.utils.Consts.HELLO_WINDOW;

@Service
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final BotCache botCache;

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage responseMsg = new SendMessage();
        responseMsg.setChatId(chatId);
        responseMsg.setText(HELLO_WINDOW);
        botCache.setBotState(update.getMessage().getFrom().getId(), BotState.BASIC_STATE);
        return responseMsg;
    }
}
