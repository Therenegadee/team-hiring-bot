package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.utils.KeyboardFactory;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.enums.Role;

import java.util.Objects;

import static rassvet.team.hire.bot.cache.enums.BotState.ADMIN_STATE;
import static rassvet.team.hire.bot.cache.enums.BotState.WATCHING_STAFF;
import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class AdminBoardCommand implements Command {
    private final BotCache botCache;
    private final BotService botService;
    private final UserDao userDao;

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
                .replyMarkup(KeyboardFactory.adminBoardKeyboard(update, telegramId))
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
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(KeyboardFactory.adminBoardKeyboard(update, telegramId))
                .build());
    }

    private void processApplicationsBoardInput(Update update) {
        Message message = update.getMessage();
        String userInput = message.getText();
        switch (userInput) {
            case SHOW_ACTIVE_APPLICATIONS_BUTTON -> showActiveApplications(update);
        }
    }

    private void showVacanciesBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(KeyboardFactory.adminBoardKeyboard(update, telegramId))
                .build());
    }

    private void processVacanciesBoardInput(Update update) {
        Message message = update.getMessage();
        String userInput = message.getText();
        switch (userInput) {
            case SHOW_OPEN_VACANCIES_BUTTON -> showOpenVacancies(update);
        }
    }

    private void showStaffBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(KeyboardFactory.adminBoardKeyboard(update, telegramId))
                .build());

    }

    private void processStaffBoardInput(Update update) {
        Message message = update.getMessage();
        String userInput = message.getText();
        switch (userInput) {
            case SHOW_CURRENT_STAFF -> showCurrentStaff(update);
        }
    }

    private void showActiveApplications(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
    }

    private void showOpenVacancies(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
    }

    private void showCurrentStaff(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        Role role = userDao.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(update))
                .getRole();
        if (!Objects.equals(role, Role.CREATOR)) {
            throw new UnknownCommandException(update);
        }
        userDao.findAll().forEach(user -> sendStaffInfo(user, chatId));
        botCache.setBotState(telegramId, WATCHING_STAFF);
    }

    private void sendStaffInfo(User user, String chatId) {
        String userInfo = String.format("""
                        Имя и Фамилия: %s
                        Должность: %s
                        Номер телефона: %s
                        Telegram: %s
                        """,
                user.getFullName(),
                user.getRole().getValue(),
                user.getPhoneNumber(),
                user.getUsername()
        );
        botService.sendResponse(
                SendMessage.builder()
                        .chatId(chatId)
                        .disableNotification(true)
                        .text(userInfo)
                        .replyMarkup()
                        .build()
        );
    }
}
