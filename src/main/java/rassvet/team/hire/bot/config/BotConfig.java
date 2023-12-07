package rassvet.team.hire.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rassvet.team.hire.bot.RassvetBot;
import rassvet.team.hire.bot.handler.UpdateHandler;

import static rassvet.team.hire.bot.handler.commands.BotCommands.LIST_OF_COMMAND;

@Configuration
public class BotConfig {
    @Autowired
    private UpdateHandler updateHandler;
    @Autowired
    private TelegramBotsApi telegramBotsApi;

    @Bean
    @Scope(scopeName = "singleton")
    public RassvetBot rassvetBot() throws TelegramApiException {
        RassvetBot rassvetBot = new RassvetBot(updateHandler);
        telegramBotsApi.registerBot(rassvetBot);
        rassvetBot.execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
        return rassvetBot;
    }

}
