package rassvet.team.hire.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.RassvetBot;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.BotState;
import rassvet.team.hire.bot.keyboards.KeyboardFactory;
import rassvet.team.hire.bot.utils.PhoneNumberFormatter;
import rassvet.team.hire.bot.utils.Validator;
import rassvet.team.hire.models.ApplicationEntity;
import rassvet.team.hire.models.enums.ContactMethod;
import rassvet.team.hire.models.enums.Position;

import java.util.Objects;

import static java.lang.Enum.valueOf;
import static rassvet.team.hire.bot.cache.BotState.*;
import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class ApplyCommand implements Command {
    private final BotCache botCache;
    private final RassvetBot rassvetBot;

    @Override
    public void handleCommand(Update update, BotState botState) {
        switch (botState) {
            case BASIC_STATE, CHOOSE_POSITION_STATE -> choosePosition(update);
            case INPUT_NAME_STATE -> inputName(update);
            case INPUT_AGE_STATE -> inputAge(update);
            case INPUT_PHONE_NUMBER_STATE -> inputPhoneNumber(update);
            case CHOOSE_CONTACT_METHOD_STATE -> inputContactMethod(update);
            case INPUT_EXPERIENCE_STATE -> inputExperience(update);
            case INPUT_EDUCATION_STATE -> inputEducation(update);
            case INPUT_DIRECTIONS_IN_PREFER_STATE -> inputDirectionsInPrefer(update);
            case INPUT_HOURS_PER_WEEK_IN_PREFER_STATE -> inputHoursPerWeekInPrefer(update);
            case INPUT_POSSIBLE_WORKING_DAYS_AND_HOURS_STATE -> inputDaysAndHoursInPrefer(update);
        }
    }

    @Override
    public void handleTextInput(Update update, BotState botState) {
        switch (botState) {
            case CHOOSE_POSITION_STATE -> savePosition(update);
            case INPUT_NAME_STATE -> saveName(update);
            case INPUT_AGE_STATE -> saveAge(update);
            case INPUT_PHONE_NUMBER_STATE -> savePhoneNumber(update);
            case CHOOSE_CONTACT_METHOD_STATE -> saveContactMethod(update);
            case INPUT_EXPERIENCE_STATE -> saveExperience(update);
            case INPUT_EDUCATION_STATE -> saveEducation(update);
            case INPUT_DIRECTIONS_IN_PREFER_STATE -> saveDirectionsInPrefer(update);
            case INPUT_HOURS_PER_WEEK_IN_PREFER_STATE -> saveHoursPerWeekInPrefer(update);
            case INPUT_POSSIBLE_WORKING_DAYS_AND_HOURS_STATE -> saveDaysAndHoursInPrefer(update);
        }
    }


    private void choosePosition(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botCache.setBotState(telegramId, CHOOSE_POSITION_STATE);
        rassvetBot.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(CHOOSE_DESIRED_POSITION)
                .replyMarkup(KeyboardFactory.positionKeyboard())
                .build());
    }

    private void savePosition(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        Position position = valueOf(Position.class, userResponse);
        if (Objects.isNull(position)) {
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_INPUT_FOR_KEYBOARDS)
                    .replyMarkup(KeyboardFactory.positionKeyboard())
                    .build());
        }
        ApplicationEntity application = new ApplicationEntity();
        application.setPosition(position);
        botCache.setApplicationEntity(telegramId, application);
        botCache.setBotState(telegramId, INPUT_NAME_STATE);
        rassvetBot.sendResponse(SendMessage.builder()
                .text("Вы претендуете на позицию тренера!")
                .chatId(chatId)
                .build());
        inputName(update);
    }

    private SendMessage inputName(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        return SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_NAME)
                .build();
    }

    private void saveName(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        if (!Validator.isNameValid(userResponse)) {
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_NAME_INPUT)
                    .build());
            return;
        }
        ApplicationEntity application = botCache.getApplicationEntity(telegramId);
        if (application.getName().isEmpty()) {
            botCache.setBotState(telegramId, INPUT_AGE_STATE);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text(userResponse + ", приятно познакомиться!")
                    .chatId(chatId)
                    .build());
            inputAge(update);
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили свое имя на: " + userResponse)
                    .chatId(chatId)
                    .build());
        }
        application.setName(userResponse);
        botCache.setApplicationEntity(telegramId, application);
    }

    private SendMessage inputAge(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        return SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_AGE)
                .build();
    }

    private void saveAge(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        if (!Validator.isAgeValid(userResponse)) {
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_AGE_INPUT)
                    .build());
            return;
        }
        int age = Integer.parseInt(userResponse);
        ApplicationEntity application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getAge())) {
            botCache.setBotState(telegramId, INPUT_PHONE_NUMBER_STATE);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text("Вы успешно ввели ваш возраст!")
                    .chatId(chatId)
                    .build());
            inputPhoneNumber(update);
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили свой возраст!")
                    .chatId(chatId)
                    .build());
        }
        application.setAge(age);
        botCache.setApplicationEntity(telegramId, application);
    }

    private void inputPhoneNumber(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        rassvetBot.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_PHONE_NUMBER)
                .build());
    }

    private void savePhoneNumber(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        if (!Validator.isPhoneNumberValid(userResponse)) {
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_PHONE_NUMBER_INPUT)
                    .build());
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text("Пожалуйста, введите корректный номер телефона!")
                    .build());
            return;
        }
        String phoneNumber = PhoneNumberFormatter.formatPhoneNumber(userResponse);
        ApplicationEntity application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getPhoneNumber())) {
            botCache.setBotState(telegramId, CHOOSE_CONTACT_METHOD_STATE);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text("Вы успешно ввели ваш номер телефона! (" + phoneNumber + ")")
                    .chatId(chatId)
                    .build());
            inputContactMethod(update);
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили свой номер телефона!(" + phoneNumber + ")")
                    .chatId(chatId)
                    .build());
        }
        application.setPhoneNumber(phoneNumber);
        botCache.setApplicationEntity(telegramId, application);
    }

    private void inputContactMethod(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        rassvetBot.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_PHONE_NUMBER)
                .replyMarkup(KeyboardFactory.contactOptions())
                .build());
    }
    private void saveContactMethod(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        ContactMethod contactMethod = valueOf(ContactMethod.class, userResponse);
        if (Objects.isNull(contactMethod)) {
            rassvetBot.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_INPUT_FOR_KEYBOARDS)
                    .replyMarkup(KeyboardFactory.contactOptions())
                    .build());
        }
        ApplicationEntity application = botCache.getApplicationEntity(telegramId);
        application.setContactMethod(contactMethod);
        botCache.setApplicationEntity(telegramId, application);
        botCache.setBotState(telegramId, INPUT_EXPERIENCE_STATE);
        rassvetBot.sendResponse(SendMessage.builder()
                .text("Вы выбрали метод для связи: " + contactMethod.getValue())
                .chatId(chatId)
                .build());
        inputExperience(update);
    }

    private void inputExperience(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        rassvetBot.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_EXPERIENCE)
                .build());
    }

    private void saveExperience(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        ApplicationEntity application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getExperience())) {
            botCache.setBotState(telegramId, INPUT_HOURS_PER_WEEK_IN_PREFER_STATE);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text("Информация о вашем опыте работы успешно сохранена!")
                    .chatId(chatId)
                    .build());
            inputHoursPerWeekInPrefer(update);
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION);
            rassvetBot.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили информацию об опыте работы!")
                    .chatId(chatId)
                    .build());
        }
        application.setExperience(userResponse);
        botCache.setApplicationEntity(telegramId, application);
    }

    private void inputEducation(Update update) {
        // ЕСЛИ УЧИШЬСЯ ТО КАКАЯ ФОРМА + ОБРАЗОВАНИЕ И КУРСЫ ПОВЫШЕНИЯ КВАЛ
    }

    private void saveEducation(Update update){

    }

    private void inputHoursPerWeekInPrefer(Update update) {

    }

    private void saveHoursPerWeekInPrefer(Update update) {

    }

    private void inputDirectionsInPrefer(Update update) {

    }

    private void saveDirectionsInPrefer(Update update) {

    }

    private void inputDaysAndHoursInPrefer(Update update){

    }

    private void saveDaysAndHoursInPrefer(Update update){

    }

}
