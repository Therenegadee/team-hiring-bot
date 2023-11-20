package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.keyboards.UserBoardKeyboardMarkUp;
import rassvet.team.hire.dao.interfaces.RoleDao;
import rassvet.team.hire.models.User;

@Service
@RequiredArgsConstructor
public class StartingBoardCommand implements Command {
    private final BotService botService;
    private final BotCache botCache;
    private final RoleDao roleDao;

    @Override
    public void handleCommand(Update update, BotState botState) {
        Long telegramId = update.getMessage().getFrom().getId();
        User user = botCache.getUserCache(telegramId);
        if (user.getRole().getRoleName().equals("Кандидат")) {
            showApplicantBoard(update);
        } else {
            showAdminBoard(update);
        }
    }

    @Override
    public void handleTextInput(Update update, BotState botState) {
        Long telegramId = update.getMessage().getFrom().getId();
        User user = botCache.getUserCache(telegramId);
        if (user.getRole().getRoleName().equals("Кандидат")) {
            showApplicantBoard(update);
        } else {
            showAdminBoard(update);
        }
    }

    private void showAdminBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(UserBoardKeyboardMarkUp.adminBoardKeyboard(update, telegramId))
                .build());
    }

    private void showApplicantBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(UserBoardKeyboardMarkUp.adminBoardKeyboard(update, telegramId))
                .build());
    }

}
