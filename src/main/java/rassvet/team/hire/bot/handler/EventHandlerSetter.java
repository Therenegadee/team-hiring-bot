package rassvet.team.hire.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.handler.interfaces.EventHandler;
import rassvet.team.hire.bot.service.interfaces.ApplicationService;
import rassvet.team.hire.bot.service.interfaces.BotService;

@Service
@RequiredArgsConstructor
public class EventHandlerSetter {
    private final BotCache botCache;
    private final BotService botService;
    private final ApplicationService applicationService;

    public EventHandler setEventHandler(Update update){
        BotState botState = botCache.getBotState(update.getMessage().getFrom().getId());
        switch (botState) {
            case INPUT_AGE_STATE, INPUT_EXPERIENCE_STATE, INPUT_FULL_NAME_STATE,
                    INPUT_PHONE_NUMBER_STATE,
                    CHOOSE_CONTACT_METHOD_STATE, ANSWERING_EXTRA_QUESTIONS_STATE -> {
                return new ApplyingEventsHandler(applicationService);
            }
            default -> throw new UnknownCommandException(update);
        }
    }
}
