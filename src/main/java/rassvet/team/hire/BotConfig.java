package rassvet.team.hire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rassvet.team.hire.bot.RassvetBot;
import rassvet.team.hire.bot.handler.UpdateHandler;

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
        return rassvetBot;
    }

}
