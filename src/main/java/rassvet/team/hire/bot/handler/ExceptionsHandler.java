package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.*;
import rassvet.team.hire.bot.service.interfaces.BotService;

import static rassvet.team.hire.bot.utils.Consts.*;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {
    private final BotService botService;
    private final BotCache botCache;

    @ExceptionHandler(InternalException.class)
    public void handleInternalException(InternalException ex) {
        Update update = ex.getUpdate();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INTERNAL_EXCEPTION)
                .build());
        botCache.setBotState(update.getMessage().getFrom().getId(), BotState.APPLICANT_STATE);
    }

    @ExceptionHandler(BadTextRequestException.class)
    public void handleBadTextRequestException(BadTextRequestException ex) {
        Update update = ex.getUpdate();
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(CANT_UNDERSTAND)
                .build());
        botCache.setBotState(update.getMessage().getFrom().getId(), BotState.APPLICANT_STATE);
    }

    @ExceptionHandler(UnknownCommandException.class)
    public void handleUnknownCommandException(UnknownCommandException ex) {
        Update update = ex.getUpdate();
        //TODO: написать логику
        botCache.setBotState(update.getMessage().getFrom().getId(), BotState.APPLICANT_STATE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(UserNotFoundException ex) {
        Update update = ex.getUpdate();
        //TODO: написать логику
        botCache.setBotState(update.getMessage().getFrom().getId(), BotState.APPLICANT_STATE);
    }

    @ExceptionHandler(IncorrectSecretKeyException.class)
    public void handleIncorrectSecretKeyException(IncorrectSecretKeyException ex) {
        Update update = ex.getUpdate();
        //TODO: написать логику
        botCache.setBotState(update.getMessage().getFrom().getId(), BotState.APPLICANT_STATE);
    }

    public void handleMisunderstandableInputException(MisunderstandableInputException ex) {
        Update update = ex.getUpdate();
        //TODO: написать логику
        botCache.setBotState(update.getMessage().getFrom().getId(), BotState.APPLICANT_STATE);
    }
}
