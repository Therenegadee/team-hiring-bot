package rassvet.team.hire.bot.keyboards;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import rassvet.team.hire.models.Role;

import java.util.ArrayList;
import java.util.List;

import static rassvet.team.hire.bot.utils.Consts.*;

@Component
@AllArgsConstructor
public class VacanciesKeyboardMarkUp {

    public static InlineKeyboardMarkup vacancyBoardKeyboard(Role role) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder()
                .text(SHOW_OPEN_VACANCIES_BUTTON)
                .callbackData("VACANCIES SHOW ALL")
                .build());
        if (!role.getRoleName().equals("Кандидат")) {
            buttons.add(InlineKeyboardButton.builder()
                    .text(CREATE_VACANCY_BUTTON)
                    .callbackData("VACANCIES CREATE")
                    .build());
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

    public static InlineKeyboardMarkup showVacancyKeyboard(Long vacancyId) {
        List<InlineKeyboardButton> buttons = List.of(
                InlineKeyboardButton.builder()
                        .text(SHOW_VACANCY_BUTTON)
                        .callbackData("VACANCIES SHOW ID " + vacancyId)
                        .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }

    public static InlineKeyboardMarkup actionsTowardVacancyKeyboard(Long vacancyId, Role role) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder()
                .text(APPLY_FOR_VACANCY_BUTTON)
                .callbackData("VACANCIES APPLY " + vacancyId)
                .build());
        if (!role.getRoleName().equals("Кандидат")) {
            buttons.add(InlineKeyboardButton.builder()
                    .text(EDIT_VACANCY_BUTTON)
                    .callbackData("VACANCIES EDIT " + vacancyId)
                    .build());
            buttons.add(InlineKeyboardButton.builder()
                    .text(DELETE_VACANCY_BUTTON)
                    .callbackData("VACANCIES DELETE " + vacancyId)
                    .build());
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }
}
