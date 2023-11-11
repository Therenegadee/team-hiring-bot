package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface VacancyBoardService {
    void showVacanciesBoard(Update update);

    void processVacanciesBoardInput(Update update);

    void showOpenVacancies(Update update);
}
