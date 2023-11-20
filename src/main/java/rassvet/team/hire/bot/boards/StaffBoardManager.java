package rassvet.team.hire.bot.boards;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.bot.boards.interfaces.BoardManager;
import rassvet.team.hire.bot.keyboards.StaffKeyboardMarkUp;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.handler.interfaces.CallbackQueryHandler;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import java.util.Objects;

import static rassvet.team.hire.bot.cache.enums.BotState.WATCHING_STAFF_STATE;

@Service
@NoArgsConstructor
public class StaffBoardManager implements BoardManager, CallbackQueryHandler {
    @Autowired
    private BotCache botCache;
    @Autowired
    private BotService botService;
    @Autowired
    private UserDao userDao;

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
                .replyMarkup(StaffKeyboardMarkUp.staffBoardKeyboard())
                .build());
    }


    public void showApplicantBoardPanel(Update update) {

    }

    private void showCurrentStaff(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        Role role = userDao.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(update))
                .getRole();
        if (!Objects.equals(role, "Создатель")) {
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
                user.getRole().getRoleName(),
                user.getPhoneNumber(),
                user.getUsername()
        );
        botService.sendResponse(
                SendMessage.builder()
                        .chatId(chatId)
                        .disableNotification(true)
                        .text(userInfo)
                        .replyMarkup(StaffKeyboardMarkUp.actionsTowardsStaffKeyboard(user))
                        .build()
        );
    }

    private void createNewStaffMember(Update update) {

    }
}
