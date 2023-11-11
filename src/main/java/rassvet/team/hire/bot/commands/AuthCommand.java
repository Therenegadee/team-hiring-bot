package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.cache.enums.StaffAuthenticationState;
import rassvet.team.hire.bot.exceptions.IncorrectSecretKeyException;
import rassvet.team.hire.bot.exceptions.UserNotFoundException;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.User;

import java.util.Objects;

import static rassvet.team.hire.bot.utils.Consts.HELLO_WINDOW_FOR_STAFF;
import static rassvet.team.hire.bot.utils.Consts.INPUT_YOUR_SECRET_CODE;

@Service
@RequiredArgsConstructor
public class AuthCommand implements Command {
    private final BotCache botCache;
    private final BotService botService;
    private final UserDao userDao;


    @Override
    public void handleCommand(Update update, BotState botState) {
        switch (botState) {
            case APPLICANT_STATE, INPUT_SECRET_STAFF_CODE_STATE -> inputSecretCode(update);
        }
    }

    @Override
    public void handleTextInput(Update update, BotState botState) {
        switch (botState) {
            case INPUT_SECRET_STAFF_CODE_STATE -> verifySecretCode(update);
        }
    }

    private void inputSecretCode(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_YOUR_SECRET_CODE)
                .build());
    }

    private void verifySecretCode(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        User user = userDao.findByTelegramId(telegramId).orElseThrow(() -> new UserNotFoundException(update));
        String secretCode = user.getSecretKey();
        String secretCodeInput = update.getMessage().getText().trim();
        if (!Objects.equals(secretCode, secretCodeInput)) {
            throw new IncorrectSecretKeyException(update);
        } else {
            String chatId = update.getMessage().getChatId().toString();
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(HELLO_WINDOW_FOR_STAFF)
                    .build());
            botCache.setBotState(telegramId, BotState.ADMIN_STATE);
            botCache.setStaffAuthState(telegramId, StaffAuthenticationState.AUTHENTICATED);
        }
    }
}
