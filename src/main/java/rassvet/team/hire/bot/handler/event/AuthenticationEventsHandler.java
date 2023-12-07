package rassvet.team.hire.bot.handler.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.AuthService;

@Service
@RequiredArgsConstructor
public class AuthenticationEventsHandler implements EventHandler {
    private final AuthService authService;

    @Override
    public void handleEvent(Update update, BotState botState) {
        switch (botState) {
            case INPUT_SECRET_STAFF_CODE_STATE, VERIFYING_SECRET_STAFF_CODE_STATE -> processSecretCodeInput(update, botState);
        }
    }

    private void processSecretCodeInput(Update update, BotState botState) {
        authService.processSecretCodeInput(update, botState);
    }

}
