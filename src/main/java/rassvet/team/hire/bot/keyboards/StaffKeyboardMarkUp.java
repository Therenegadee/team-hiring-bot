package rassvet.team.hire.bot.keyboards;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import rassvet.team.hire.models.User;

import java.util.List;

import static rassvet.team.hire.bot.utils.Consts.*;
import static rassvet.team.hire.bot.utils.Consts.SHOW_CURRENT_STAFF;

@Component
@AllArgsConstructor
public class StaffKeyboardMarkUp {

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
}
