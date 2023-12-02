package rassvet.team.hire.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.cache.BotCache;
import rassvet.team.hire.bot.cache.enums.BotState;
import rassvet.team.hire.bot.keyboards.VacanciesKeyboardMarkUp;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.service.interfaces.VacancyService;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.Vacancy;
import rassvet.team.hire.models.enums.ApplicationStatus;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import static rassvet.team.hire.bot.utils.Consts.*;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final BotCache botCache;
    private final BotService botService;
    private final VacancyDao vacancyDao;
    private final UserDao userDao;

    @Override
    public void showVacancies(Update update) {
        Set<Vacancy> vacancies = vacancyDao.findAll();
        sendVacancyBriefInfo(update, vacancies);
    }

    private void sendVacancyBriefInfo(Update update, Set<Vacancy> vacancies) {
        String chatId = update.getMessage().getChatId().toString();
        String vacancyInfo = "Вакансия: %s";
        for (Vacancy vacancy : vacancies) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format(
                            vacancyInfo,
                            vacancy.getPositionName()

                    ))
                    .replyMarkup(VacanciesKeyboardMarkUp.showVacancyKeyboard(vacancy.getId()))
                    .build()
            );
        }
    }

    @Override
    public void showVacancy(Update update, Long vacancyId) {
        //todo: добавить обработку исключения
        Vacancy vacancy = vacancyDao.findById(vacancyId)
                .orElseThrow(RuntimeException::new);
        sendVacancyWholeInfo(update, vacancy);
    }

    private void sendVacancyWholeInfo(Update update, Vacancy vacancy) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        User user = botCache.getUserCache(telegramId);
        Role role = user.getRole();

        String vacancyInfo = """
                Должность: %s
                Описание вакансии:
                %s
                """;
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(String.format(
                        vacancyInfo,
                        vacancy.getPositionName(),
                        vacancy.getDescription()
                ))
                .replyMarkup(VacanciesKeyboardMarkUp.actionsTowardVacancyKeyboard(vacancy.getId(), role))
                .build());
    }

    @Override
    public void applyForVacancy(Update update, Long vacancyId) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        //TODO: добавить обработку исключения
        Vacancy vacancy = vacancyDao.findById(vacancyId).orElseThrow(RuntimeException::new);
        Application application = new Application();
        application.setApplicationStatus(ApplicationStatus.IN_CONSIDERATION);
        application.setTelegramId(telegramId.toString());
        application.setVacancy(vacancy);
        application.setAnswers(new HashMap<>());
        botCache.setApplicationEntity(telegramId, application);
        botCache.setBotState(telegramId, BotState.INPUT_FULL_NAME_STATE);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_NAME)
                .build());
    }

    @Override
    public void createVacancy(Update update){

    }

    @Override
    public void editVacancy(Update update, Long vacancyId) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        //TODO: добавить обработку исключения
        Vacancy vacancy = vacancyDao.findById(vacancyId).orElseThrow(RuntimeException::new);
        botCache.setVacancyEntity(telegramId, vacancy);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(WHAT_TO_EDIT)
                .replyMarkup(VacanciesKeyboardMarkUp.editVacancyKeyboard(vacancyId))
                .build());
    }

    @Override
    public void editDescription(Update update, Long vacancyId) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        Vacancy vacancy = botCache.getVacancyEntity(telegramId);
        if(Objects.isNull(vacancy) && !vacancy.getId().equals(vacancyId)) {
            //TODO: добавить обработку исключения
            vacancy = vacancyDao.findById(vacancyId).orElseThrow(RuntimeException::new);
            botCache.setVacancyEntity(telegramId, vacancy);
        }
        botCache.setBotState(telegramId, BotState.INPUT_VACANCY_DESCRIPTION_STATE);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_VACANCY_DESCRIPTION)
                .build());
    }

    @Override
    public void editPosition(Update update, Long vacancyId) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        Vacancy vacancy = botCache.getVacancyEntity(telegramId);
        if(Objects.isNull(vacancy) && !vacancy.getId().equals(vacancyId)) {
            //TODO: добавить обработку исключения
            vacancy = vacancyDao.findById(vacancyId).orElseThrow(RuntimeException::new);
            botCache.setVacancyEntity(telegramId, vacancy);
        }
        botCache.setBotState(telegramId, BotState.INPUT_VACANCY_POSITION_STATE);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_VACANCY_POSITION)
                .build());
    }

    @Override
    public void editQuestionnaire(Update update, Long vacancyId) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        Vacancy vacancy = botCache.getVacancyEntity(telegramId);
        if(Objects.isNull(vacancy) && !vacancy.getId().equals(vacancyId)) {
            //TODO: добавить обработку исключения
            vacancy = vacancyDao.findById(vacancyId).orElseThrow(RuntimeException::new);
            botCache.setVacancyEntity(telegramId, vacancy);
        }
        botCache.setBotState(telegramId, BotState.INPUT_VACANCY_QUESTIONNAIRE_STATE);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(INPUT_VACANCY_QUESTIONNAIRE)
                .build());
    }

    @Override
    public void deleteVacancy(Update update, Long vacancyId) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        //TODO: добавить обработку исключения
        Vacancy vacancy = vacancyDao.findById(vacancyId).orElseThrow(RuntimeException::new);
        botCache.setVacancyEntity(telegramId, vacancy);
        vacancyDao.deleteById(vacancyId);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(VACANCY_SUCCESSFULLY_DELETED)
                .replyMarkup(VacanciesKeyboardMarkUp.restoreVacancyKeyboard())
                .build());
    }

    @Override
    public void restoreVacancy(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        Vacancy vacancy = botCache.getVacancyEntity(telegramId);
        vacancyDao.save(vacancy);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(VACANCY_SUCCESSFULLY_RESTORED)
                .build());
    }
}
