package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.RassvetBot;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.BotState;
import rassvet.team.hire.bot.commands.Command;
import rassvet.team.hire.bot.commands.CommandSetter;

import java.util.Objects;

import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class UpdateHandler {
    private final CommandSetter commandSetter;
    private final BotCache botCache;
    private final RassvetBot rassvetBot;

    public void handleUpdate(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        BotState botState = botCache.getBotState(telegramId);
        if(processBasicCases(update, botState) == 1) {
            return;
        }
        Command command = commandSetter.setCommand(botState, update);
        if (Objects.isNull(command)) {
            internalError(update);
        } else {
            if(update.getMessage().getText().startsWith("/")) {
                command.handleCommand(update, botState);
            } else {
                command.handleTextInput(update, botState);
            }
        }
    }

    private void internalError(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        rassvetBot.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INTERNAL_ERROR)
                .build());
    }

    private void textRequestError(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        rassvetBot.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(CANT_UNDERSTAND)
                .build());
    }

    private int processBasicCases(Update update, BotState botState) {
        String messageRequest = update.getMessage().getText();
        if (botState.equals(BotState.BASIC_STATE) && !update.getMessage().getText().startsWith("/")) {
            textRequestError(update);
            return 1;
        } else if (messageRequest.equals("/info")) {
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(INFO_WINDOW)
                    .build());
            return 1;
        } else if (messageRequest.equals("/help")) {
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(HELP_WINDOW)
                    .build());
            return 1;
        } else if(messageRequest.equals("/start")){
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(HELLO_WINDOW)
                    .build());
            return 1;
        }
        else {
            return 0;
        }
    }
}
