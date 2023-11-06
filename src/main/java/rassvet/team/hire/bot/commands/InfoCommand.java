package rassvet.team.hire.bot.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import static rassvet.team.hire.bot.utils.Consts.INFO_WINDOW;

@Service
public class InfoCommand implements Command{
    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage responseMsg = new SendMessage();
        responseMsg.setChatId(chatId);
        responseMsg.setText(INFO_WINDOW);
        return null;
    }
}
