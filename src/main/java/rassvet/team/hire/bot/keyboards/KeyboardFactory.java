package rassvet.team.hire.bot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import rassvet.team.hire.models.enums.ContactMethod;
import rassvet.team.hire.models.enums.Position;

import java.util.List;

@Component
public class KeyboardFactory {


    public static ReplyKeyboard positionKeyboard(){
        KeyboardRow row = new KeyboardRow();
        row.add(Position.TRAINER.getValue());
        row.add(Position.ADMINISTRATOR.getValue());
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard contactOptions(){
        KeyboardRow row = new KeyboardRow();
        row.add(ContactMethod.TELEGRAM.getValue());
        row.add(ContactMethod.WHATSAPP.getValue());
        return new ReplyKeyboardMarkup(List.of(row));
    }
}
