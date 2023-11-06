package rassvet.team.hire.bot.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ApplyCommand implements Command{
    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
