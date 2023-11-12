package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.service.interfaces.ApplicationsBoardService;
import rassvet.team.hire.bot.service.interfaces.StaffBoardService;
import rassvet.team.hire.bot.service.interfaces.VacancyBoardService;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandler {
    private final ApplicationsBoardService applicationsBoardService;
    private final StaffBoardService staffBoardService;
    private final VacancyBoardService vacancyBoardService;

    public void handle(Update update){
        String callbackData = update.getCallbackQuery().getData();
        String callbackDataPrefix = callbackData.split(" ")[0];
        switch (callbackDataPrefix) {
            case "APPLICATIONS" -> applicationsBoardService.handleCallbackQuery(update, callbackData);
            case "STAFF" -> staffBoardService.handleCallbackQuery(update, callbackData);
            case "VACANCIES" -> vacancyBoardService.handleCallbackQuery(update, callbackData);
        }
    }
}
