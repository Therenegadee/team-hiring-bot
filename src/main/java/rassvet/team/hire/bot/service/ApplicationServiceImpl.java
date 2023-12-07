package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.service.interfaces.ApplicationService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.keyboards.ReplyMarkupKeyboardFactory;
import rassvet.team.hire.bot.utils.PhoneNumberFormatter;
import rassvet.team.hire.bot.utils.Validator;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.enums.ContactMethod;

import java.util.Objects;

import static rassvet.team.hire.bot.cache.enums.BotState.*;
import static rassvet.team.hire.bot.cache.enums.BotState.EDITING_APPLICATION_STATE;
import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final BotCache botCache;
    private final BotService botService;

    @Override
    public void processNameInput(Update update, BotState botState) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        if (!botState.equals(SAVING_FULL_NAME_STATE)) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INPUT_NAME)
                    .build());
            botCache.setBotState(telegramId, SAVING_FULL_NAME_STATE);
        } else {
            saveName(update, botState);
        }
    }

    public void saveName(Update update, BotState botState) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        if (!Validator.isNameValid(userResponse)) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_NAME_INPUT)
                    .build());
            botCache.setBotState(telegramId, INPUT_FULL_NAME_STATE);
            return;
        }
        Application application = botCache.getApplicationEntity(telegramId);
        if (application.getFullName().isEmpty()) {
            botService.sendResponse(SendMessage.builder()
                    .text(userResponse + ", приятно познакомиться!")
                    .chatId(chatId)
                    .build());
            botState = INPUT_AGE_STATE;
            botCache.setBotState(telegramId, INPUT_AGE_STATE);
            processAgeInput(update, botState);
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION_STATE);
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили свое имя на: " + userResponse)
                    .chatId(chatId)
                    .build());
        }
        application.setFullName(userResponse);
        botCache.setApplicationEntity(telegramId, application);
    }

    @Override
    public void processAgeInput(Update update, BotState botState) {
        String chatId = update.getMessage().getChatId().toString();
        if (!botState.equals(SAVING_AGE_STATE)) {
            Long telegramId = update.getMessage().getFrom().getId();
            botCache.setBotState(telegramId, SAVING_AGE_STATE);
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INPUT_AGE)
                    .build());
        } else {
            saveAge(update, botState);
        }
    }

    public void saveAge(Update update, BotState botState) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        if (!Validator.isAgeValid(userResponse)) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_AGE_INPUT)
                    .build());
            return;
        }
        Integer age = Integer.parseInt(userResponse);
        Application application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getAge())) {
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно ввели ваш возраст!")
                    .chatId(chatId)
                    .build());
            botState = INPUT_PHONE_NUMBER_STATE;
            botCache.setBotState(telegramId, INPUT_PHONE_NUMBER_STATE);
            processPhoneNumberInput(update, botState);
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION_STATE);
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили свой возраст!")
                    .chatId(chatId)
                    .build());
        }
        application.setAge(age);
        botCache.setApplicationEntity(telegramId, application);
    }

    @Override
    public void processPhoneNumberInput(Update update, BotState botState) {
        String chatId = update.getMessage().getChatId().toString();
        if (!botState.equals(SAVING_PHONE_NUMBER_STATE)) {
            Long telegramId = update.getMessage().getFrom().getId();
            botCache.setBotState(telegramId, SAVING_PHONE_NUMBER_STATE);
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INPUT_PHONE_NUMBER)
                    .build());
        } else {
            savePhoneNumber(update, botState);
        }
    }

    public void savePhoneNumber(Update update, BotState botState) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        if (!Validator.isPhoneNumberValid(userResponse)) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_PHONE_NUMBER_INPUT)
                    .build());
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text("Пожалуйста, введите корректный номер телефона!")
                    .build());
            return;
        }
        String phoneNumber = PhoneNumberFormatter.formatPhoneNumber(userResponse);
        Application application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getPhoneNumber())) {
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно ввели ваш номер телефона! (" + phoneNumber + ")")
                    .chatId(chatId)
                    .build());
            botState = CHOOSE_CONTACT_METHOD_STATE;
            botCache.setBotState(telegramId, CHOOSE_CONTACT_METHOD_STATE);
            processContactMethodInput(update, botState);
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION_STATE);
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили свой номер телефона!(" + phoneNumber + ")")
                    .chatId(chatId)
                    .build());
        }
        application.setPhoneNumber(phoneNumber);
        botCache.setApplicationEntity(telegramId, application);
    }

    @Override
    public void processContactMethodInput(Update update, BotState botState) {
        String chatId = update.getMessage().getChatId().toString();
        if (!botState.equals(SAVING_CONTACT_METHOD_STATE)) {
            Long telegramId = update.getMessage().getFrom().getId();
            botCache.setBotState(telegramId, SAVING_CONTACT_METHOD_STATE);
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INPUT_PHONE_NUMBER)
                    .replyMarkup(ReplyMarkupKeyboardFactory.contactOptions())
                    .build());
        } else {
            saveContactMethod(update, botState);
        }

    }

    public void saveContactMethod(Update update, BotState botState) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        ContactMethod contactMethod = valueOf(ContactMethod.class, userResponse);
        if (Objects.isNull(contactMethod)) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_INPUT_FOR_KEYBOARDS)
                    .replyMarkup(ReplyMarkupKeyboardFactory.contactOptions())
                    .build());
        }
        Application application = botCache.getApplicationEntity(telegramId);
        application.setContactMethod(contactMethod);
        botCache.setApplicationEntity(telegramId, application);
        botService.sendResponse(SendMessage.builder()
                .text("Вы выбрали метод для связи: " + contactMethod.getValue())
                .chatId(chatId)
                .build());
        botState = INPUT_EXPERIENCE_STATE;
        botCache.setBotState(telegramId, INPUT_EXPERIENCE_STATE);
        processExperienceInput(update, botState);
    }

    @Override
    public void processExperienceInput(Update update, BotState botState) {
        String chatId = update.getMessage().getChatId().toString();
        if (!botState.equals(SAVING_EXPERIENCE_STATE)) {
            Long telegramId = update.getMessage().getFrom().getId();
            botCache.setBotState(telegramId, SAVING_EXPERIENCE_STATE);
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INPUT_EXPERIENCE)
                    .build());
        } else {
            saveExperience(update, botState);
        }
    }

    public void saveExperience(Update update, BotState botState) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        Application application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getExperience())) {
            botService.sendResponse(SendMessage.builder()
                    .text("Информация о вашем опыте работы успешно сохранена!")
                    .chatId(chatId)
                    .build());
            if (!application.getVacancy().getQuestions().isEmpty()) {
                botState = ANSWERING_EXTRA_QUESTIONS_STATE;
                botCache.setBotState(telegramId, ANSWERING_EXTRA_QUESTIONS_STATE);
            } else {
                botCache.setBotState(telegramId, FINISHED_QUESTIONNAIRE_STATE);
                //TODO: метод просмотра всей анкеты (ответы на вопросы) + возможность редакции
            }
        } else {
            botCache.setBotState(telegramId, EDITING_APPLICATION_STATE);
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно изменили информацию об опыте работы!")
                    .chatId(chatId)
                    .build());
        }
        application.setExperience(userResponse);
        botCache.setApplicationEntity(telegramId, application);
    }
}
