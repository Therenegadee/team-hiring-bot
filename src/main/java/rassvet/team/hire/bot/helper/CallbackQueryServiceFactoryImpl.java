package rassvet.team.hire.bot.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rassvet.team.hire.bot.helper.interfaces.CallbackQueryServiceFactory;
import rassvet.team.hire.bot.boards.ApplicationsBoardManager;
import rassvet.team.hire.bot.boards.StaffBoardManager;
import rassvet.team.hire.bot.boards.VacancyBoardManager;
import rassvet.team.hire.bot.handler.interfaces.CallbackQueryHandler;

@Service
@RequiredArgsConstructor
public class CallbackQueryServiceFactoryImpl implements CallbackQueryServiceFactory {
    @Override
    public CallbackQueryHandler createService(String callbackData) {
        String firstPrefix = callbackData.split(" ")[0];
        switch (firstPrefix) {
            case "APPLICATIONS" -> {
                return new ApplicationsBoardManager();
            }
            case "STAFF" -> {
                return new StaffBoardManager();
            }
            case "VACANCIES" -> {
                return new VacancyBoardManager();
            }
            //todo: добавить обработку исключения
            default -> throw new RuntimeException();
        }
    }
}
