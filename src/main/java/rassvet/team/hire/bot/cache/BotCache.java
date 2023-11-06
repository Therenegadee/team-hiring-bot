package rassvet.team.hire.bot.cache;

import org.springframework.stereotype.Component;
import rassvet.team.hire.models.ApplicationEntity;

import java.util.HashMap;
import java.util.Map;

import static rassvet.team.hire.bot.cache.BotState.BASIC_STATE;

@Component
public class BotCache {
    private final Map<Long, BotState> botStateCache = new HashMap<>();
    private final Map<Long, ApplicationEntity> applicationEntityCache = new HashMap<>();


    public void setBotState(Long telegramUserId, BotState botState) {
        botStateCache.put(telegramUserId, botState);
    }

    public BotState getBotState(Long telegramId) {
        BotState botState = botStateCache.get(telegramId);
        if (botState == null) {
            botStateCache.put(telegramId, BASIC_STATE);
        }
        return botStateCache.get(telegramId);
    }

    public void setApplicationEntity(Long telegramId, ApplicationEntity applicationEntity) {
        applicationEntityCache.put(telegramId, applicationEntity);
    }

    public ApplicationEntity getApplicationEntity(Long telegramId) {
        return applicationEntityCache.get(telegramId);
    }
}
