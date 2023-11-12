package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.bot.service.interfaces.BoardService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.CallbackQueryService;
import rassvet.team.hire.bot.service.interfaces.StaffBoardService;
import rassvet.team.hire.bot.utils.InlineKeyboardMarkupFactory;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.enums.Role;

import java.util.Objects;

import static rassvet.team.hire.bot.cache.enums.BotState.WATCHING_STAFF_STATE;

@Service
@RequiredArgsConstructor
public class StaffBoardServiceImpl implements BoardService, CallbackQueryService {
    private final BotCache botCache;
    private final BotService botService;
    private final UserDao userDao;

    @Override
    public void handleCallbackQuery(Update update, String callbackData) {
        String secondPrefixOfCallbackData = callbackData.split(" ")[1];
        switch (secondPrefixOfCallbackData) {
            case "BOARD" -> showBoardPanel(update);
            case "DELETE" -> ;
            case "EDIT" -> ;
            case "ADD" -> ;
            case "SHOW" -> showCurrentStaff(update);
        }
    }

    @Override
    public void showBoardPanel(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(InlineKeyboardMarkupFactory.staffBoardKeyboard())
                .build());
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
        userDao.findAll()
                .forEach(user -> sendStaffInfo(user, chatId));
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
                        .replyMarkup(InlineKeyboardMarkupFactory.actionsTowardsStaffKeyboard(user))
                        .build()
        );
    }

    private void createNewStaffMember(Update update) {

    }
}
