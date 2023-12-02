package rassvet.team.hire.bot.commands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMAND = List.of(
            new BotCommand("/start", "Запуск бота"),
            new BotCommand("/help", "Список доступных команд"),
            new BotCommand("/info", "Информация о боте"),
            new BotCommand("/vacancies", "Активные вакансии")
    );
}
