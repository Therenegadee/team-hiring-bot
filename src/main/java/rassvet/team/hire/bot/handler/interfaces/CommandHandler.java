package rassvet.team.hire.bot.handler.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void handleCommand(Update update);
}
