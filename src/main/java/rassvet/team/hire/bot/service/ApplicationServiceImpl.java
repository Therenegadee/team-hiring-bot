package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.service.interfaces.ApplicationService;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.utils.KeyboardFactory;
import rassvet.team.hire.bot.utils.PhoneNumberFormatter;
import rassvet.team.hire.bot.utils.Validator;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.Vacancy;
import rassvet.team.hire.models.enums.ContactMethod;

import java.util.Objects;
import java.util.Optional;

import static rassvet.team.hire.bot.cache.enums.BotState.*;
import static rassvet.team.hire.bot.cache.enums.BotState.EDITING_APPLICATION_STATE;
import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final BotCache botCache;
    private final BotService botService;
    private final VacancyDao vacancyDao;

    @Override
    public void choosePosition(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        botCache.setBotState(telegramId, CHOOSE_POSITION_STATE);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(CHOOSE_DESIRED_POSITION)
                .replyMarkup(KeyboardFactory.positionKeyboard())
                .build());
    }

    @Override
    public void savePosition(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        Application application = new Application();
        Optional<Vacancy> questionnaireOpt = vacancyDao.findByPositionName(userResponse);
        if (questionnaireOpt.isEmpty()) {
            botService.sendResponse(SendMessage.builder()
                    .text(INCORRECT_INPUT_FOR_KEYBOARDS)
                    .chatId(chatId)
                    .replyMarkup(KeyboardFactory.positionKeyboard())
                    .build());
        }
        Vacancy vacancy = questionnaireOpt.get();
        application.setVacancy(vacancy);
        botCache.setApplicationEntity(telegramId, application);
        botCache.setBotState(telegramId, INPUT_FULL_NAME_STATE);
        botService.sendResponse(SendMessage.builder()
                .text("Вы претендуете на позицию: " + vacancy.getPositionName())
                .chatId(chatId)
                .build());
        inputName(update);
    }

    @Override
    public void inputName(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_NAME)
                .build());
    }

    @Override
    public void saveName(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        if (!Validator.isNameValid(userResponse)) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_NAME_INPUT)
                    .build());
            return;
        }
        Application application = botCache.getApplicationEntity(telegramId);
        if (application.getFullName().isEmpty()) {
            botCache.setBotState(telegramId, INPUT_AGE_STATE);
            botService.sendResponse(SendMessage.builder()
                    .text(userResponse + ", приятно познакомиться!")
                    .chatId(chatId)
                    .build());
            inputAge(update);
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
    public void inputAge(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_AGE)
                .build());
    }

    @Override
    public void saveAge(Update update) {
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
        int age = Integer.parseInt(userResponse);
        Application application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getAge())) {
            botCache.setBotState(telegramId, INPUT_PHONE_NUMBER_STATE);
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно ввели ваш возраст!")
                    .chatId(chatId)
                    .build());
            inputPhoneNumber(update);
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
    public void inputPhoneNumber(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_PHONE_NUMBER)
                .build());
    }

    @Override
    public void savePhoneNumber(Update update) {
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
            botCache.setBotState(telegramId, CHOOSE_CONTACT_METHOD_STATE);
            botService.sendResponse(SendMessage.builder()
                    .text("Вы успешно ввели ваш номер телефона! (" + phoneNumber + ")")
                    .chatId(chatId)
                    .build());
            inputContactMethod(update);
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
    public void inputContactMethod(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_PHONE_NUMBER)
                .replyMarkup(KeyboardFactory.contactOptions())
                .build());
    }

    @Override
    public void saveContactMethod(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        ContactMethod contactMethod = valueOf(ContactMethod.class, userResponse);
        if (Objects.isNull(contactMethod)) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(INCORRECT_INPUT_FOR_KEYBOARDS)
                    .replyMarkup(KeyboardFactory.contactOptions())
                    .build());
        }
        Application application = botCache.getApplicationEntity(telegramId);
        application.setContactMethod(contactMethod);
        botCache.setApplicationEntity(telegramId, application);
        botCache.setBotState(telegramId, INPUT_EXPERIENCE_STATE);
        botService.sendResponse(SendMessage.builder()
                .text("Вы выбрали метод для связи: " + contactMethod.getValue())
                .chatId(chatId)
                .build());
        inputExperience(update);
    }

    @Override
    public void inputExperience(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_EXPERIENCE)
                .build());
    }

    @Override
    public void saveExperience(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String userResponse = update.getMessage().getText();
        Application application = botCache.getApplicationEntity(telegramId);
        if (Objects.isNull(application.getExperience())) {
            botService.sendResponse(SendMessage.builder()
                    .text("Информация о вашем опыте работы успешно сохранена!")
                    .chatId(chatId)
                    .build());
            if(!application.getVacancy().getQuestions().isEmpty()) {
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
