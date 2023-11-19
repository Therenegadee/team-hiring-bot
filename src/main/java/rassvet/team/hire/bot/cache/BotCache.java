package rassvet.team.hire.bot.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.cache.enums.StaffAuthenticationState;
import rassvet.team.hire.dao.interfaces.RoleDao;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static rassvet.team.hire.bot.cache.enums.BotState.APPLICANT_STATE;

@Component
@RequiredArgsConstructor
public class BotCache {
    private final Map<Long, BotState> botStateCache = new HashMap<>();
    private final Map<Long, Application> applicationEntityCache = new HashMap<>();
    private final Map<Long, StaffAuthenticationState> staffAuthStateCache = new HashMap<>();
    private final Map<Long, User> userCache = new HashMap<>();
    private final RoleDao roleDao;


    public User getUserCache(Long telegramId) {
        User user = userCache.get(telegramId);
        if (Objects.isNull(user)) {
            user = new User();
            user.setTelegramId(telegramId.toString());
            //TODO: добавить обработку исключения
            Role role = roleDao.findByRoleName("Кандидат").orElseThrow(RuntimeException::new);
            user.setRole(role);
        }
        return user;
    }

    public void setUserCache(Long telegramId, User user) {
        userCache.put(telegramId, user);
    }

    public void setBotState(Long telegramId, BotState botState) {
        botStateCache.put(telegramId, botState);
    }

    public BotState getBotState(Long telegramId) {
        botStateCache.putIfAbsent(telegramId, APPLICANT_STATE);
        return botStateCache.get(telegramId);
    }

    public void setStaffAuthState(Long telegramId, StaffAuthenticationState staffAuthState) {
        staffAuthStateCache.put(telegramId, staffAuthState);
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
