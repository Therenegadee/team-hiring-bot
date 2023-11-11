package rassvet.team.hire.bot.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.enums.Role;

import java.util.ArrayList;
import java.util.List;

import static rassvet.team.hire.bot.utils.Consts.*;

@Component
@AllArgsConstructor
public class InlineKeyboardMarkupFactory {
    private static UserDao userDao;

    public static InlineKeyboardMarkup actionsTowardsStaffKeyboard(User user) {
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text(EDIT_STAFF_MEMBER_BUTTON)
                .callbackData("EDIT " + user.getId())
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text(DELETE_STAFF_MEMBER_BUTTON)
                .callbackData("DELETE " + user.getId())
                .build();
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(button1, button2))
                .build();
    }

    public static InlineKeyboardMarkup applicationsBoardKeyboard(Update update) {
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text(SHOW_ACTIVE_APPLICATIONS_BUTTON)
                .callbackData("APPLICATIONS SHOW ACTIVE")
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text(SHOW_ALL_APPLICATIONS_BUTTON)
                .callbackData("APPLICATIONS SHOW ALL")
                .build();
        InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                .text(SHOW_REFUSED_APPLICATIONS_BUTTON)
                .callbackData("APPLICATIONS SHOW REFUSED")
                .build();
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(button1, button2, button3))
                .build();
    }

    public static InlineKeyboardMarkup adminBoardKeyboard(Update update, Long telegramId) {
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        Role role = userDao.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(update))
                .getRole();
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text(APPLICATIONS_BUTTON)
                .callbackData("APPLICATIONS SHOW")
                .build();
        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text(VACANCIES_BUTTON)
                .callbackData("VACANCIES SHOW")
                .build();
        buttonList.add(button1);
        buttonList.add(button2);
        if (role.equals(Role.CREATOR)) {
            InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                    .text(STAFF_BUTTON)
                    .callbackData("STAFF SHOW")
                    .build();
            buttonList.add(button3);
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttonList)
                .build();
    }
}
