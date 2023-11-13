package rassvet.team.hire.bot.boards;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.boards.interfaces.BoardManager;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.handler.interfaces.CallbackQueryHandler;
import rassvet.team.hire.bot.utils.InlineKeyboardMarkupFactory;


@Component
@NoArgsConstructor
public class VacancyBoardManager implements BoardManager, CallbackQueryHandler {
    @Autowired
    private BotCache botCache;
    @Autowired
    private BotService botService;

    @Override
    public void handleCallbackQuery(Update update, String callbackData) {
        String secondPrefixOfCallbackData = callbackData.split(" ")[1];
        switch (secondPrefixOfCallbackData) {
            case "BOARD" -> showBoardPanel(update);
        }
    }

    @Override
    public void showBoardPanel(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                //TODO: CHANGE KEYBOARD
                .replyMarkup(InlineKeyboardMarkupFactory.adminBoardKeyboard(update, telegramId))
                .build());
    }

    public void showOpenVacancies(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
    }
}
