package rassvet.team.hire.bot.handler.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.service.interfaces.ApplicationService;
import rassvet.team.hire.bot.service.interfaces.AuthService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventHandlerSetter {
    private final BotCache botCache;
    private final ApplicationService applicationService;
    private final AuthService authService;

    public EventHandler setEventHandler(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        BotState botState = botCache.getBotState(update.getMessage().getFrom().getId());
        EventHandler eventHandler = botCache.getEventHandlerCache(telegramId);
        switch (botState) {
            case INPUT_AGE_STATE, SAVING_AGE_STATE,
                    INPUT_EXPERIENCE_STATE, SAVING_EXPERIENCE_STATE,
                    INPUT_FULL_NAME_STATE, SAVING_FULL_NAME_STATE,
                    INPUT_PHONE_NUMBER_STATE, SAVING_PHONE_NUMBER_STATE,
                    CHOOSE_CONTACT_METHOD_STATE, SAVING_CONTACT_METHOD_STATE,
                    ANSWERING_EXTRA_QUESTIONS_STATE -> {
                return (Objects.isNull(eventHandler) || !(eventHandler instanceof ApplyingEventsHandler)) ?
                        new ApplyingEventsHandler(applicationService) : botCache.getEventHandlerCache(telegramId);
            }
            case INPUT_SECRET_STAFF_CODE_STATE, VERIFYING_SECRET_STAFF_CODE_STATE -> {
                return (Objects.isNull(eventHandler) || !(eventHandler instanceof AuthenticationEventsHandler)) ?
                        new AuthenticationEventsHandler(authService) : eventHandler;
            }
            default -> throw new UnknownCommandException(update);
        }
    }
}
