package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.StaffBoardService;
import rassvet.team.hire.bot.utils.ReplyMarkupKeyboardFactory;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.enums.Role;

import java.util.Objects;

import static rassvet.team.hire.bot.cache.enums.BotState.WATCHING_STAFF_STATE;
import static rassvet.team.hire.bot.utils.Consts.SHOW_CURRENT_STAFF;

@Service
@RequiredArgsConstructor
public class StaffBoardServiceImpl implements StaffBoardService {
    private final BotCache botCache;
    private final BotService botService;
    private final UserDao userDao;

    @Override
    public void showStaffBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(ReplyMarkupKeyboardFactory.adminBoardKeyboard(update, telegramId))
                .build());
    }

    @Override
    public void processStaffBoardInput(Update update) {
        Message message = update.getMessage();
        String userInput = message.getText();
        switch (userInput) {
            case SHOW_CURRENT_STAFF -> showCurrentStaff(update);
        }
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
        botCache.setBotState(telegramId, WATCHING_STAFF_STATE);
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
                        .replyMarkup(ReplyMarkupKeyboardFactory.actionsTowardsStaffKeyboard(user))
                        .build()
        );
    }
}
