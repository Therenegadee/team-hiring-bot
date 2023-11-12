package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.service.interfaces.BoardService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.CallbackQueryService;
import rassvet.team.hire.bot.service.interfaces.VacancyBoardService;
import rassvet.team.hire.bot.utils.InlineKeyboardMarkupFactory;
import rassvet.team.hire.bot.utils.ReplyMarkupKeyboardFactory;

import static rassvet.team.hire.bot.utils.Consts.SHOW_OPEN_VACANCIES_BUTTON;

@Component
@RequiredArgsConstructor
public class VacancyBoardServiceImpl implements BoardService, CallbackQueryService {
    private final BotCache botCache;
    private final BotService botService;

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

    @Override
    public void showOpenVacancies(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
    }
}
