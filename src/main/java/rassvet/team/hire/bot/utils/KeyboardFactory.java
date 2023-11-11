package rassvet.team.hire.bot.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.Vacancy;
import rassvet.team.hire.models.enums.ContactMethod;
import rassvet.team.hire.models.enums.Role;

import java.util.List;
import java.util.Set;

import static rassvet.team.hire.bot.utils.Consts.*;

@Component
@AllArgsConstructor
public class KeyboardFactory {
    private static VacancyDao vacancyDao;
    private static UserDao userDao;

    public static ReplyKeyboard positionKeyboard(){
        KeyboardRow row = new KeyboardRow();
        Set<Vacancy> vacancies = vacancyDao.findAll();
        vacancies.forEach(vacancy -> row.add(vacancy.getPositionName()));
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard contactOptions(){
        KeyboardRow row = new KeyboardRow();
        row.add(ContactMethod.TELEGRAM.getValue());
        row.add(ContactMethod.WHATSAPP.getValue());
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard adminBoardKeyboard(Update update, Long telegramId){
        Role role = userDao.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(update))
                .getRole();
        KeyboardRow row = new KeyboardRow();
        row.add(APPLICATIONS_BUTTON);
        row.add(VACANCIES_BUTTON);
        if(role.equals(Role.CREATOR)) {
            row.add(STAFF_BUTTON);
        }
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard applicationsBoardKeyboard(Update update) {
        KeyboardRow row = new KeyboardRow();
        row.add(SHOW_ACTIVE_APPLICATIONS_BUTTON);
        row.add(SHOW_ALL_APPLICATIONS_BUTTON);
        row.add(SHOW_REFUSED_APPLICATIONS_BUTTON);
        return new ReplyKeyboardMarkup(List.of(row));
    }
}
