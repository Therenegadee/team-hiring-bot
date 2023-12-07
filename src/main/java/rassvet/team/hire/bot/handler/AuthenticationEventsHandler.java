package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.handler.interfaces.EventHandler;
import rassvet.team.hire.bot.service.interfaces.AuthService;

@Service
@RequiredArgsConstructor
public class AuthenticationEventsHandler implements EventHandler {
    private final AuthService authService;

    @Override
    public void handleEvent(Update update) {

    }

//    @Override
//    public void handleCommand(Update update, BotState botState) {
//        switch (botState) {
//            case APPLICANT_STATE, INPUT_SECRET_STAFF_CODE_STATE -> inputSecretCode(update);
//        }
//    }
//
//    @Override
//    public void handleTextInput(Update update, BotState botState) {
//        switch (botState) {
//            case INPUT_SECRET_STAFF_CODE_STATE -> verifySecretCode(update);
//        }
//    }

    private void inputSecretCode(Update update) {
        authService.inputSecretCode(update);
    }

    private void verifySecretCode(Update update) {
        authService.verifySecretCode(update);
    }
}
