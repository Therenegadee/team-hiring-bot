package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rassvet.team.hire.bot.RassvetBot;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.BadTextRequestException;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {
    private final RassvetBot rassvetBot;
    private final UserDao userDao;

    @Override
    public void sendResponse(SendMessage responseMsg) {
        try {
            rassvetBot.execute(responseMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int processBasicCases(Update update, BotState botState) {
        String messageRequest = update.getMessage().getText();
        Long telegramId = update.getMessage().getFrom().getId();
        if (!messageRequest.startsWith("/")) {
            throw new BadTextRequestException(update);
        }
        //TODO: добавить обработку исключения
        User user = userDao.findByTelegramId(telegramId).orElseThrow();
        Role role = user.getRole();
        if (!role.equals("Кандидат")) {
            return processStaffBasicCases(update);
        } else {
            return processApplicantBasicCases(messageRequest, update);
        }
    }

    private int processApplicantBasicCases(String messageRequest, Update update) {
        switch (messageRequest) {
            case "/start" -> {
                sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELLO_WINDOW)
                        .build());
                return 1;
            }
            case "/info" -> {
                sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(INFO_WINDOW)
                        .build());
                return 1;
            }
            case "/help" -> {
                sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELP_WINDOW)
                        .build());
                return 1;
            }
            default -> {
                return 0;
            }
        }
    }

    private int processStaffBasicCases(Update update) {
        String messageRequest = update.getMessage().getText();
        return switch (messageRequest) {
            case "/start" -> {
                sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELLO_WINDOW_FOR_STAFF)
                        .build());
                yield 1;
            }
            case "/info" -> {
                sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(INFO_WINDOW_FOR_STAFF)
                        .build());
                yield 1;
            }
            case "/help" -> {
                sendResponse(SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELP_WINDOW_FOR_STAFF)
                        .build());
                yield 1;
            }
            default -> 0;
        };
    }
}
