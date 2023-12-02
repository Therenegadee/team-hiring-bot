package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface VacancyService {
    void showVacancies(Update update);

    void showVacancy(Update update, Long vacancyId);

    void applyForVacancy(Update update, Long vacancyId);

    void createVacancy(Update update);

    void editVacancy(Update update, Long vacancyId);

    void editDescription(Update update, Long vacancyId);

    void editPosition(Update update, Long vacancyId);

    void editQuestionnaire(Update update, Long vacancyId);

    void deleteVacancy(Update update, Long vacancyId);

    void restoreVacancy(Update update);
}
