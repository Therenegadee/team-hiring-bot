package rassvet.team.hire.bot.handler.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void handleCommand(Update update);
}
