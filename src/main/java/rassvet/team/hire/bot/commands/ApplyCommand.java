package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.ApplicationService;

@Service
@RequiredArgsConstructor
public class ApplyCommand implements Command {
    private final ApplicationService applicationService;

    @Override
    public void handleCommand(Update update, BotState botState) {
        switch (botState) {
            case APPLICANT_STATE, CHOOSE_POSITION_STATE -> choosePosition(update);
            case INPUT_FULL_NAME_STATE -> inputName(update);
            case INPUT_AGE_STATE -> inputAge(update);
            case INPUT_PHONE_NUMBER_STATE -> inputPhoneNumber(update);
            case CHOOSE_CONTACT_METHOD_STATE -> inputContactMethod(update);
            case INPUT_EXPERIENCE_STATE -> inputExperience(update);
        }
    }

    @Override
    public void handleTextInput(Update update, BotState botState) {
        switch (botState) {
            case CHOOSE_POSITION_STATE -> savePosition(update);
            case INPUT_FULL_NAME_STATE -> saveName(update);
            case INPUT_AGE_STATE -> saveAge(update);
            case INPUT_PHONE_NUMBER_STATE -> savePhoneNumber(update);
            case CHOOSE_CONTACT_METHOD_STATE -> saveContactMethod(update);
            case INPUT_EXPERIENCE_STATE -> saveExperience(update);
        }
    }


    private void choosePosition(Update update) {
        applicationService.choosePosition(update);
    }

    private void savePosition(Update update) {
        applicationService.savePosition(update);
    }

    private void inputName(Update update) {
        applicationService.inputName(update);
    }

    private void saveName(Update update) {
        applicationService.saveName(update);
    }

    private void inputAge(Update update) {
        applicationService.inputAge(update);
    }

    private void saveAge(Update update) {
        applicationService.saveAge(update);
    }

    private void inputPhoneNumber(Update update) {
        applicationService.inputPhoneNumber(update);
    }

    private void savePhoneNumber(Update update) {
        applicationService.savePhoneNumber(update);
    }

    private void inputContactMethod(Update update) {
        applicationService.inputContactMethod(update);
    }

    private void saveContactMethod(Update update) {
        applicationService.saveContactMethod(update);
    }

    private void inputExperience(Update update) {
        applicationService.inputExperience(update);
    }

    private void saveExperience(Update update) {
        applicationService.saveExperience(update);
    }

}
