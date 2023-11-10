package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.BotState;
import rassvet.team.hire.bot.service.BotService;
import rassvet.team.hire.dao.interfaces.VacancyDao;

@Service
@RequiredArgsConstructor
public class CommandSetter {
    private final BotCache botCache;
    private final BotService botService;
    private final VacancyDao vacancyDao;

    public Command setCommand(Update update) {
        if(update.getMessage().getText().startsWith("/")) {
            setBotStateByCommand(update);
        }
        BotState botState = botCache.getBotState(update.getMessage().getFrom().getId());
        switch (botState) {
            case INPUT_AGE_STATE, INPUT_EXPERIENCE_STATE, INPUT_FULL_NAME_STATE,
                    INPUT_PHONE_NUMBER_STATE, CHOOSE_POSITION_STATE,
                    CHOOSE_CONTACT_METHOD_STATE,  ANSWERING_EXTRA_QUESTIONS_STATE -> {
                return new ApplyCommand(botCache, botService, vacancyDao);
            }
            case ADMIN_STATE -> {
                return new AdminBoardCommand();
            }
            default -> {
                return null;
            }
        }
    }

    private void setBotStateByCommand(Update update) {
        String requestMessage = update.getMessage().getText();
        Long telegramId = update.getMessage().getFrom().getId();
        BotState botState = switch (requestMessage) {
            case "/apply" -> BotState.CHOOSE_POSITION_STATE;
            case "/login" -> BotState.INPUT_SECRET_STAFF_CODE;
            default -> botCache.getBotState(telegramId);
        };
        botCache.setBotState(telegramId, botState);
    }
}
