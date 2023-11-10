package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.exceptions.BadTextRequestException;
import rassvet.team.hire.bot.exceptions.InternalException;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.service.BotService;

import static rassvet.team.hire.bot.utils.Consts.*;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {
    private final BotService botService;

    @ExceptionHandler(InternalException.class)
    public void handleInternalException(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INTERNAL_EXCEPTION)
                .build());
    }

    @ExceptionHandler(BadTextRequestException.class)
    public void handleBadTextRequestException(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(CANT_UNDERSTAND)
                .build());
    }

    @ExceptionHandler(UnknownCommandException.class)
    public void handleUnknownCommandException(Update update) {

    }
}
