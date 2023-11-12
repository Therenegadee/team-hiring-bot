package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.ApplicationsBoardService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.StaffBoardService;
import rassvet.team.hire.bot.service.interfaces.VacancyBoardService;
import rassvet.team.hire.bot.utils.InlineKeyboardMarkupFactory;

@Service
@RequiredArgsConstructor
public class AdminBoardCommand implements Command {
    private final BotService botService;

    @Override
    public void handleCommand(Update update, BotState botState) {
        showAdminBoard(update);
    }

    @Override
    public void handleTextInput(Update update, BotState botState) {
        showAdminBoard(update);
    }

    private void showAdminBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(InlineKeyboardMarkupFactory.adminBoardKeyboard(update, telegramId))
                .build());
    }

}
