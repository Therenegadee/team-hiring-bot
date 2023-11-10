package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.BotState;
import rassvet.team.hire.bot.exceptions.IncorrectSecretKeyException;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.bot.service.BotService;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.User;

import java.util.Objects;

import static rassvet.team.hire.bot.utils.Consts.HELLO_WINDOW_FOR_STAFF;
import static rassvet.team.hire.bot.utils.Consts.INPUT_YOUR_SECRET_CODE;

@Service
@RequiredArgsConstructor
public class LoginStaffCommand implements Command {
    private final BotCache botCache;
    private final BotService botService;
    private final UserDao userDao;


    @Override
    public void handleCommand(Update update, BotState botState) {
        switch (botState) {
            case BASIC_STATE, INPUT_SECRET_STAFF_CODE -> inputSecretCode(update);
        }
    }

    @Override
    public void handleTextInput(Update update, BotState botState) {
        switch (botState) {
            case INPUT_SECRET_STAFF_CODE -> verifySecretCode(update);
        }
    }

    private void verifySecretCode(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        User user = userDao.findByTelegramId(telegramId).orElseThrow(UserNotFoundException::new);
        String secretCode = user.getSecretKey();
        String secretCodeInput = update.getMessage().getText().trim();
        if (!Objects.equals(secretCode, secretCodeInput)) {
            throw new IncorrectSecretKeyException();
        } else {
            String chatId = update.getMessage().getChatId().toString();
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(HELLO_WINDOW_FOR_STAFF)
                    .build());
        }
    }

    private void inputSecretCode(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_YOUR_SECRET_CODE)
                .build());
    }
}
