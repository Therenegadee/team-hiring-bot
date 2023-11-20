package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.exceptions.UnknownCommandException;
import rassvet.team.hire.bot.service.interfaces.ApplicationService;
import rassvet.team.hire.bot.service.interfaces.BotService;

@Service
@RequiredArgsConstructor
public class CommandSetter {
    private final BotCache botCache;
    private final BotService botService;
    private final ApplicationService applicationService;

    public Command setCommand(Update update) {
        if(update.getMessage().getText().startsWith("/")) {
            setBotStateByCommand(update);
        }
        BotState botState = botCache.getBotState(update.getMessage().getFrom().getId());
        switch (botState) {
            case INPUT_AGE_STATE, INPUT_EXPERIENCE_STATE, INPUT_FULL_NAME_STATE,
                    INPUT_PHONE_NUMBER_STATE, CHOOSE_POSITION_STATE,
                    CHOOSE_CONTACT_METHOD_STATE,  ANSWERING_EXTRA_QUESTIONS_STATE -> {
                return new ApplyCommand(applicationService);
            }
            case ADMIN_STATE -> {
                return new StartingBoardCommand(botService);
            }
            default -> throw new UnknownCommandException(update);
        }
    }

    private void setBotStateByCommand(Update update) {
        String requestMessage = update.getMessage().getText();
        Long telegramId = update.getMessage().getFrom().getId();
        BotState botState = switch (requestMessage) {
            case "/admin" -> BotState.ADMIN_STATE;
            case "/apply" -> BotState.CHOOSE_POSITION_STATE;
            case "/auth" -> BotState.INPUT_SECRET_STAFF_CODE_STATE;
            default -> botCache.getBotState(telegramId);
        };
        botCache.setBotState(telegramId, botState);
    }
}
