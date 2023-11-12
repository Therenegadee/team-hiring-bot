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
        List<InlineKeyboardButton> buttons = List.of(
                InlineKeyboardButton.builder()
                        .text(EDIT_STAFF_MEMBER_BUTTON)
                        .callbackData("STAFF EDIT " + user.getId())
                        .build(),
                InlineKeyboardButton.builder()
                        .text(DELETE_STAFF_MEMBER_BUTTON)
                        .callbackData("STAFF DELETE " + user.getId())
                        .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

    public static InlineKeyboardMarkup staffBoardKeyboard() {
        List<InlineKeyboardButton> buttons = List.of(
        InlineKeyboardButton.builder()
                .text(SHOW_CURRENT_STAFF)
                .callbackData("STAFF SHOW")
                .build(),
        InlineKeyboardButton.builder()
                .text(SHOW_CURRENT_STAFF)
                .callbackData("STAFF ADD")
                .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

    public static InlineKeyboardMarkup applicationsBoardKeyboard() {
        List<InlineKeyboardButton> buttons = List.of(
                InlineKeyboardButton.builder()
                        .text(SHOW_ACTIVE_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW ACTIVE")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(SHOW_ALL_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW ALL")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(SHOW_REFUSED_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW REFUSED")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(SHOW_REFUSED_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW ARCHIVE")
                        .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

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
        if (role.equals(Role.CREATOR)) {
            buttons.add(InlineKeyboardButton.builder()
                    .text(STAFF_BUTTON)
                    .callbackData("STAFF BOARD")
                    .build());
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }
}
