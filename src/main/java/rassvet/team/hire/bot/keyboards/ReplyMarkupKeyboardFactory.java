package rassvet.team.hire.bot.keyboards;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.Vacancy;
import rassvet.team.hire.models.enums.ContactMethod;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class ReplyMarkupKeyboardFactory {

    public static ReplyKeyboard contactOptions(){
        KeyboardRow row = new KeyboardRow();
        row.add(ContactMethod.TELEGRAM.getValue());
        row.add(ContactMethod.WHATSAPP.getValue());
        return new ReplyKeyboardMarkup(List.of(row));
    }

}
