package rassvet.team.hire.bot.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import rassvet.team.hire.dao.interfaces.QuestionnaireDao;
import rassvet.team.hire.models.Questionnaire;
import rassvet.team.hire.models.enums.ContactMethod;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class KeyboardFactory {
    private static QuestionnaireDao questionnaireDao;
    public static ReplyKeyboard positionKeyboard(){
        KeyboardRow row = new KeyboardRow();
        Set<Questionnaire> questionnaires = questionnaireDao.findAll();
        questionnaires.forEach(questionnaire -> row.add(questionnaire.getPosition()));
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard contactOptions(){
        KeyboardRow row = new KeyboardRow();
        row.add(ContactMethod.TELEGRAM.getValue());
        row.add(ContactMethod.WHATSAPP.getValue());
        return new ReplyKeyboardMarkup(List.of(row));
    }
}
