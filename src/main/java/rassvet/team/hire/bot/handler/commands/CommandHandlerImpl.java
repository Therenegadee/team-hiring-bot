package rassvet.team.hire.bot.handler.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.exceptions.BadTextRequestException;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.handler.commands.CommandHandler;
import rassvet.team.hire.bot.keyboards.UserBoardKeyboardMarkUp;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.dao.interfaces.RoleDao;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import static rassvet.team.hire.bot.utils.Consts.*;
import static rassvet.team.hire.bot.utils.Consts.HELP_WINDOW_FOR_STAFF;

@Service
@RequiredArgsConstructor
public class CommandHandlerImpl implements CommandHandler {
    private final UserDao userDao;
    private final BotCache botCache;
    private final RoleDao roleDao;
    private final BotService botService;

    @Override
    public void handleCommand(Update update) {
        String messageRequest = update.getMessage().getText();
        Long telegramId = update.getMessage().getFrom().getId();
        if (!messageRequest.startsWith("/")) {
            throw new BadTextRequestException(update);
        }
        //TODO: добавить обработку исключения
        User user = userDao.findByTelegramId(telegramId).orElseThrow();
        Role role = user.getRole();
        if (!role.getRoleName().equals("Кандидат")) {
            processStaffCommandInput(update);
        } else {
            processApplicantCommandInput(messageRequest, update);
        }
    }

    private void processApplicantCommandInput(String messageRequest, Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        switch (messageRequest) {
            case "/start" -> {
                User user;
                if (userDao.existsByTelegramId(telegramId)) {
                    //todo: добавить обработку исключения
                    user = userDao.findByTelegramId(telegramId).orElseThrow(RuntimeException::new);
                } else {
                    user = new User();
                    //todo: добавить обработку исключения
                    Role role = roleDao.findByRoleName("Кандидат").orElseThrow(RuntimeException::new);
                    user.setRole(role);
                    user.setTelegramId(telegramId.toString());
                    user.setUsername(update.getMessage().getFrom().getUserName());
                    userDao.save(user);
                }
                botCache.setUserCache(telegramId, user);
                botService.sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELLO_WINDOW)
                        .replyMarkup(UserBoardKeyboardMarkUp.applicantBoardKeyboard(update, telegramId))
                        .build());
            }
            case "/info" -> botService.sendResponse(SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(INFO_WINDOW)
                    .replyMarkup(UserBoardKeyboardMarkUp.applicantBoardKeyboard(update, telegramId))
                    .build());

            case "/help" -> botService.sendResponse(SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(HELP_WINDOW)
                    .replyMarkup(UserBoardKeyboardMarkUp.applicantBoardKeyboard(update, telegramId))
                    .build());

            default -> throw new UnknownCommandException(update);
        }
    }

    private void processStaffCommandInput(Update update) {
        String messageRequest = update.getMessage().getText();
        Long telegramId = update.getMessage().getFrom().getId();
        switch (messageRequest) {
            case "/start" -> botService.sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELLO_WINDOW_FOR_STAFF)
                        .replyMarkup(UserBoardKeyboardMarkUp.adminBoardKeyboard(update, telegramId))
                        .build());
            case "/info" -> botService.sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(INFO_WINDOW_FOR_STAFF)
                        .replyMarkup(UserBoardKeyboardMarkUp.adminBoardKeyboard(update, telegramId))
                        .build());
            case "/help" -> botService.sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELP_WINDOW_FOR_STAFF)
                        .replyMarkup(UserBoardKeyboardMarkUp.adminBoardKeyboard(update, telegramId))
                        .build());
            default -> throw new UnknownCommandException(update);
        }
    }
}
