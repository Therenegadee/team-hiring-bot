package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.service.interfaces.ApplicationsBoardService;
import rassvet.team.hire.bot.service.interfaces.BoardService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.CallbackQueryService;
import rassvet.team.hire.bot.utils.InlineKeyboardMarkupFactory;
import rassvet.team.hire.models.enums.ApplicationStatus;

@Service
@RequiredArgsConstructor
public class ApplicationsBoardServiceImpl implements BoardService, CallbackQueryService {
    private final BotCache botCache;
    private final BotService botService;

    @Override
    public void handleCallbackQuery(Update update, String callbackData) {
        String secondPrefixOfCallbackData = callbackData.split(" ")[1];
        switch (secondPrefixOfCallbackData) {
            case "BOARD" -> showBoardPanel(update);
            case "SHOW" -> {
                String thirdPrefixOfCallbackData = callbackData.split(" ")[2];
                switch (thirdPrefixOfCallbackData) {
                    case "ALL" -> showApplications(update);
                    case "ACTIVE" -> showApplications(update, ApplicationStatus.IN_CONSIDERATION);
                    case "REFUSED" -> showApplications(update, ApplicationStatus.REFUSED);
                    case "ARCHIVE" -> showApplications(update, ApplicationStatus.ARCHIVE);
                }
            }
        }
    }
    @Override
    public void showBoardPanel(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(InlineKeyboardMarkupFactory.applicationsBoardKeyboard())
                .build());
    }


    public void showApplications(Update update, ApplicationStatus applicationStatus) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();

    }

    public void showApplications(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
    }
}
