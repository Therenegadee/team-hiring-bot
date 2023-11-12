package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.AuthService;

@Service
@RequiredArgsConstructor
public class AuthCommand implements Command {
    private final AuthService authService;


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
        authService.inputSecretCode(update);
    }

    private void verifySecretCode(Update update) {
        authService.verifySecretCode(update);
    }
}
