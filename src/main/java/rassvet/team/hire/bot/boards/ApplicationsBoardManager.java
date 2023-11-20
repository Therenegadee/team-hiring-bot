package rassvet.team.hire.bot.boards;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import rassvet.team.hire.bot.boards.interfaces.BoardManager;
import rassvet.team.hire.bot.keyboards.ApplicationsKeyboardMarkUp;
import rassvet.team.hire.bot.service.interfaces.BotService;
import rassvet.team.hire.bot.handler.interfaces.CallbackQueryHandler;
import rassvet.team.hire.dao.interfaces.ApplicationDao;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.enums.ApplicationStatus;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Service
@NoArgsConstructor
public class ApplicationsBoardManager implements BoardManager, CallbackQueryHandler {
    @Autowired
    private BotService botService;
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public void handleCallbackQuery(Update update, String callbackData) {
        String[] callbackDataArr = callbackData.split(" ");
        String secondPrefixOfCallbackData = callbackDataArr[1];
        switch (secondPrefixOfCallbackData) {
            case "BOARD" -> showBoardPanel(update);
            case "APPLICANT" -> showApplicantApplications(update);
            case "EDIT" -> {
                String thirdPrefixOfCallbackData = callbackDataArr[2];
                Long applicationId = Long.parseLong(thirdPrefixOfCallbackData);
                String fourthPrefixOfCallbackData = callbackDataArr[3];
                switch (fourthPrefixOfCallbackData) {
                    case "APPROVE" -> editApplication(update, applicationId, ApplicationStatus.APPROVED);
                    case "REJECT" -> editApplication(update, applicationId, ApplicationStatus.REJECTED);
                }
            }
            case "SHOW" -> {
                String thirdPrefixOfCallbackData = callbackDataArr[2];
                switch (thirdPrefixOfCallbackData) {
                    case "ALL" -> showApplication(update);
                    case "ACTIVE" -> showApplication(update, ApplicationStatus.IN_CONSIDERATION);
                    case "REFUSED" -> showApplication(update, ApplicationStatus.REJECTED);
                    case "APPROVED" -> showApplication(update, ApplicationStatus.APPROVED);
                    case "ID" -> {
                        String fourthPrefixOfCallbackData = callbackDataArr[3];
                        Long applicationId = Long.parseLong(fourthPrefixOfCallbackData);
                        showApplication(update, applicationId);
                    }
                }
            }
        }
    }

    @Override
    public void showBoardPanel(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(ApplicationsKeyboardMarkUp.applicationsBoardKeyboard())
                .build());
    }

    public void showApplicantApplications(Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        Set<Application> applications = applicationDao.findAllByTelegramId(telegramId);
        LocalDate currDate = LocalDate.now();
        applications.stream()
                .filter(application -> currDate.minusDays(30).isBefore(application.getDateOfCreation().toLocalDate()))
                .forEach(application -> sendApplicationWholeInfo(update, application));
    }

    private void showApplication(Update update, ApplicationStatus applicationStatus) {
        Set<Application> applications = applicationDao.findAllByStatus(applicationStatus);
        sendApplicationsBriefInfo(update, applications);
    }

    private void showApplication(Update update, Long id) {
        //TODO: добавить обработку исключения
        Application application = applicationDao.findById(id).orElseThrow(RuntimeException::new);
        sendApplicationWholeInfo(update, application);
    }


    private void showApplication(Update update) {
        Set<Application> applications = applicationDao.findAll();
        sendApplicationsBriefInfo(update, applications);
    }

    private void editApplication(Update update, Long applicationId, ApplicationStatus applicationStatus) {
        //TODO: добавить обработку исключения
        String chatId = update.getMessage().getChatId().toString();
        Application application = applicationDao.findById(applicationId).orElseThrow(RuntimeException::new);
        application.setApplicationStatus(applicationStatus);
        applicationDao.updateById(applicationId, application);
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text("Вы успешно обновили статус заявления!")
                .build()
        );
        showBoardPanel(update);
    }

    private void sendApplicationWholeInfo(Update update, Application application) {
        String chatId = update.getMessage().getChatId().toString();
        String applicationInfo = """
                Вакансия: %s
                Статус заявления: %s
                Имя и фамилия: %s
                Возраст: %d
                Номер телефона: %s
                Предпочитаемый способ связи: %s
                Опыт: %s
                Ответы на дополнительные вопросы:
                %s
                """;
        Map<String, String> questionsAndAnswers = application.getAnswers();
        StringBuilder answersToQuestions = new StringBuilder();
        for (Map.Entry<String, String> entry : questionsAndAnswers.entrySet()) {
            String question = entry.getKey();
            String answer = entry.getValue();
            answersToQuestions.append(String.format(
                    "Вопрос: %s\nОтвет: %s\n",
                    question, answer
            ));
        }
        botService.sendResponse(SendMessage.builder()
                .chatId(chatId)
                .text(String.format(
                        applicationInfo,
                        application.getVacancy().getPositionName(),
                        application.getApplicationStatus().getValue(),
                        application.getFullName(),
                        application.getAge(),
                        application.getPhoneNumber(),
                        application.getContactMethod().getValue(),
                        application.getExperience(),
                        answersToQuestions
                ))
                .replyMarkup(ApplicationsKeyboardMarkUp.briefInfoOnApplicationKeyboard(application.getId()))
                .build());
    }

    private void sendApplicationsBriefInfo(Update update, Set<Application> applications) {
        String chatId = update.getMessage().getChatId().toString();
        String applicationInfo = """
                Вакансия: %s
                Статус заявления: %s
                Имя и фамилия: %s
                Возраст: %d
                Опыт: %s
                """;

        for (Application application : applications) {
            botService.sendResponse(SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format(
                            applicationInfo,
                            application.getVacancy().getPositionName(),
                            application.getApplicationStatus().getValue(),
                            application.getFullName(),
                            application.getAge(),
                            application.getExperience()
                    ))
                    .replyMarkup(ApplicationsKeyboardMarkUp.briefInfoOnApplicationKeyboard(application.getId()))
                    .build()
            );
        }
    }
}
