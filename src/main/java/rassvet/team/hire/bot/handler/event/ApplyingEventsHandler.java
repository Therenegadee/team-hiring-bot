package rassvet.team.hire.bot.handler.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.ApplicationService;

@Service
@RequiredArgsConstructor
public class ApplyingEventsHandler implements EventHandler {
    private final ApplicationService applicationService;

    @Override
    public void handleEvent(Update update, BotState botState) {
        switch (botState) {
            case INPUT_FULL_NAME_STATE, SAVING_FULL_NAME_STATE -> processNameInput(update, botState);
            case INPUT_AGE_STATE, SAVING_AGE_STATE -> processAgeInput(update, botState);
            case INPUT_PHONE_NUMBER_STATE, SAVING_PHONE_NUMBER_STATE -> processPhoneNumberInput(update, botState);
            case CHOOSE_CONTACT_METHOD_STATE, SAVING_CONTACT_METHOD_STATE -> processContactMethodInput(update, botState);
            case INPUT_EXPERIENCE_STATE, SAVING_EXPERIENCE_STATE -> processExperienceInput(update, botState);
        }
    }

    private void processNameInput(Update update, BotState botState) {
        applicationService.processNameInput(update, botState);
    }

    private void processAgeInput(Update update, BotState botState) {
        applicationService.processAgeInput(update, botState);
    }

    private void processPhoneNumberInput(Update update, BotState botState) {
        applicationService.processPhoneNumberInput(update, botState);
    }

    private void processContactMethodInput(Update update, BotState botState) {
        applicationService.processContactMethodInput(update, botState);
    }

    private void processExperienceInput(Update update, BotState botState) {
        applicationService.processExperienceInput(update, botState);
    }
}
