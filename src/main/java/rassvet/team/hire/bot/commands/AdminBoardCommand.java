package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.ApplicationsBoardService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.StaffBoardService;
import rassvet.team.hire.bot.service.interfaces.VacancyBoardService;
import rassvet.team.hire.bot.utils.ReplyMarkupKeyboardFactory;
import java.util.Objects;

import static rassvet.team.hire.bot.cache.enums.BotState.ADMIN_STATE;
import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class AdminBoardCommand implements Command {
    private final BotService botService;
    private final ApplicationsBoardService applicationsBoardService;
    private final VacancyBoardService vacancyBoardService;
    private final StaffBoardService staffBoardService;

    @Override
    public void handleCommand(Update update, BotState botState) {
        switch (botState) {
            case ADMIN_STATE -> showAdminBoard(update);
        }
    }

    @Override
    public void handleTextInput(Update update, BotState botState) {
        if (Objects.isNull(update.getMessage()) && Objects.equals(botState, ADMIN_STATE)) {
            showAdminBoard(update);
            return;
        }
        switch (botState) {
            case ADMIN_STATE -> processAdminBoardButtonInput(update);
        }
    }

    private void showAdminBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(ReplyMarkupKeyboardFactory.adminBoardKeyboard(update, telegramId))
                .build());
    }

    private void processAdminBoardButtonInput(Update update) {
        Message message = update.getMessage();
        String userInput = message.getText();
        switch (userInput) {
            case APPLICATIONS_BUTTON -> showApplicationsBoard(update);
            case VACANCIES_BUTTON -> showVacanciesBoard(update);
            case STAFF_BUTTON -> showStaffBoard(update);
        }
    }

    private void showApplicationsBoard(Update update) {
        applicationsBoardService.showApplicationsBoard(update);
    }

    private void processApplicationsBoardInput(Update update) {
        applicationsBoardService.processApplicationsBoardInput(update);
    }

    private void showVacanciesBoard(Update update) {
        vacancyBoardService.showVacanciesBoard(update);
    }

    private void processVacanciesBoardInput(Update update) {
        vacancyBoardService.processVacanciesBoardInput(update);
    }

    private void showStaffBoard(Update update) {
        staffBoardService.showStaffBoard(update);
    }

    private void processStaffBoardInput(Update update) {
        staffBoardService.processStaffBoardInput(update);
    }

}
