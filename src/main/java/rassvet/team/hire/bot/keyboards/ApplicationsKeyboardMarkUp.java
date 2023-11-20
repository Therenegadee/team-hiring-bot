package rassvet.team.hire.bot.keyboards;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static rassvet.team.hire.bot.utils.Consts.*;

@Component
@AllArgsConstructor
public class ApplicationsKeyboardMarkUp {

    public static InlineKeyboardMarkup applicationsBoardKeyboard() {
        List<InlineKeyboardButton> buttons = List.of(
                InlineKeyboardButton.builder()
                        .text(SHOW_ACTIVE_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW ACTIVE")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(SHOW_APPROVED_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW APPROVED")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(SHOW_ALL_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW ALL")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(SHOW_REFUSED_APPLICATIONS_BUTTON)
                        .callbackData("APPLICATIONS SHOW REJECTED")
                        .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

    public static InlineKeyboardMarkup briefInfoOnApplicationKeyboard(Long applicationId){
        List<InlineKeyboardButton> buttons = List.of(
                InlineKeyboardButton.builder()
                        .text("Просмотр заявления")
                        .callbackData("APPLICATIONS SHOW ID " + applicationId)
                        .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

    public static InlineKeyboardMarkup wholeInfoOnApplicationKeyboard(Long applicationId){
        List<InlineKeyboardButton> buttons = List.of(
                InlineKeyboardButton.builder()
                        .text("Пригласить на собеседование")
                        .callbackData("APPLICATIONS EDIT APPROVE " + applicationId)
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Отклонить заявления")
                        .callbackData("APPLICATIONS EDIT REJECT " + applicationId)
                        .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

}
