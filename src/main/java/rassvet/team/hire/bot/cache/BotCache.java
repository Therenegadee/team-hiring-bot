package rassvet.team.hire.bot.cache;

import org.springframework.stereotype.Component;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.cache.enums.StaffAuthenticationState;
import rassvet.team.hire.models.Application;

import java.util.HashMap;
import java.util.Map;

import static rassvet.team.hire.bot.cache.enums.BotState.APPLICANT_STATE;

@Component
public class BotCache {
    private final Map<Long, BotState> botStateCache = new HashMap<>();
    private final Map<Long, Application> applicationEntityCache = new HashMap<>();
    private final Map<Long, StaffAuthenticationState> staffAuthStateCache = new HashMap<>();


    public void setBotState(Long telegramUserId, BotState botState) {
        botStateCache.put(telegramUserId, botState);
    }

    public BotState getBotState(Long telegramId) {
        botStateCache.putIfAbsent(telegramId, APPLICANT_STATE);
        return botStateCache.get(telegramId);
    }

    public void setStaffAuthState(Long telegramUserId, StaffAuthenticationState staffAuthState) {
        staffAuthStateCache.put(telegramUserId, staffAuthState);
    }

    public StaffAuthenticationState getStaffAuthState(Long telegramId) {
        staffAuthStateCache.putIfAbsent(telegramId, StaffAuthenticationState.LOGGED_OUT);
        return staffAuthStateCache.get(telegramId);
    }

    public void setApplicationEntity(Long telegramId, Application application) {
        applicationEntityCache.put(telegramId, application);
    }

    public Application getApplicationEntity(Long telegramId) {
        return applicationEntityCache.get(telegramId);
    }
}
