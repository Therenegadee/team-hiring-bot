package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface StaffBoardService {
    void handleCallbackQuery(Update update, String callbackData);

    void showStaffBoard(Update update);

    void processStaffBoardInput(Update update);
}
