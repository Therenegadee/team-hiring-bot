package rassvet.team.hire.bot.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.service.ApplicationsBoardServiceImpl;
import rassvet.team.hire.bot.service.StaffBoardServiceImpl;
import rassvet.team.hire.bot.service.VacancyBoardServiceImpl;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.CallbackQueryService;
import rassvet.team.hire.dao.interfaces.UserDao;

@Service
@RequiredArgsConstructor
public class CallbackQueryServiceFactoryImpl implements CallbackQueryServiceFactory {
    private final BotCache botCache;
    private final BotService botService;
    private final UserDao userDao;

    @Override
    public CallbackQueryService createService(String callbackData) {
        String firstPrefix = callbackData.split(" ")[0];
        switch (firstPrefix) {
            case "APPLICATIONS" -> {
                return new ApplicationsBoardServiceImpl(botCache, botService);
            }
            case "STAFF" -> {
                return new StaffBoardServiceImpl(botCache, botService, userDao);
            }
            case "VACANCIES" -> {
                return new VacancyBoardServiceImpl(botCache, botService);
            }
            default -> {
                return null;
            }
        }
    }
}
