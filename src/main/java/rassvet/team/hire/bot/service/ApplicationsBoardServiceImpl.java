package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.service.interfaces.ApplicationsBoardService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.utils.ReplyMarkupKeyboardFactory;

import static rassvet.team.hire.bot.utils.Consts.SHOW_ACTIVE_APPLICATIONS_BUTTON;

@Service
@RequiredArgsConstructor
public class ApplicationsBoardServiceImpl implements ApplicationsBoardService {
    private final BotCache botCache;
    private final BotService botService;

    @Override
    public void showApplicationsBoard(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(ReplyMarkupKeyboardFactory.applicationsBoardKeyboard(update))
                .build());
    }

    @Override
    public void processApplicationsBoardInput(Update update) {
        Message message = update.getMessage();
        String userInput = message.getText();
        switch (userInput) {
            case SHOW_ACTIVE_APPLICATIONS_BUTTON -> showActiveApplications(update);
        }
    }

    @Override
    public void showActiveApplications(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
    }
}
