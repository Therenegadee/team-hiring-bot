package rassvet.team.hire.bot.service.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ApplicationService {
    void choosePosition(Update update);
    void savePosition(Update update);
    void inputName(Update update);
    void saveName(Update update);
    void inputAge(Update update);
    void saveAge(Update update);
    void inputPhoneNumber(Update update);
    void savePhoneNumber(Update update);
    void inputContactMethod(Update update);
    void saveContactMethod(Update update);
    void inputExperience(Update update);
    void saveExperience(Update update);
}
