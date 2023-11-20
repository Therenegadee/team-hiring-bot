package rassvet.team.hire.bot.keyboards;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.Role;

import java.util.ArrayList;
import java.util.List;

import static rassvet.team.hire.bot.utils.Consts.*;

@Component
@AllArgsConstructor
public class UserBoardKeyboardMarkUp {
    private static UserDao userDao;

    public static InlineKeyboardMarkup adminBoardKeyboard(Update update, Long telegramId) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        Role role = userDao.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(update))
                .getRole();
        buttons.add(InlineKeyboardButton.builder()
                .text(APPLICATIONS_BUTTON)
                .callbackData("APPLICATIONS BOARD")
                .build());
        buttons.add(InlineKeyboardButton.builder()
                .text(VACANCIES_BUTTON)
                .callbackData("VACANCIES BOARD")
                .build());
        if (role.equals("Создатель")) {
            buttons.add(InlineKeyboardButton.builder()
                    .text(STAFF_BUTTON)
                    .callbackData("STAFF BOARD")
                    .build());
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

    public static InlineKeyboardMarkup applicantBoardKeyboard(Update update, Long telegramId) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder()
                .text(VACANCIES_BUTTON)
                .callbackData("VACANCIES APPLICANT")
                .build());
        buttons.add(InlineKeyboardButton.builder()
                .text(MY_APPLICATIONS_BUTTON)
                .callbackData("APPLICATIONS APPLICANT")
                .build());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

}
