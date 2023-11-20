package rassvet.team.hire.dao.interfaces;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.Vacancy;
import rassvet.team.hire.models.enums.ApplicationStatus;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ApplicationDao {
    Optional<Application> findById(Long id);

    Set<Application> findAllByTelegramId(Long telegramId);

    Set<Application> findAll();

    Map<String, String> findAnswersToQuestions(Long applicationId);

    Set<Application> findAllByVacancyId(Long vacancyId);

    Set<Application> findAllByStatus(ApplicationStatus applicationStatus);

    Set<Application> findAllByVacancyIdAndStatus(Long vacancyId, ApplicationStatus applicationStatus);

    Application save(Application application);

    Application update(Application application);

    Application updateById(Long id, Application application);

    int deleteById(Long id);

    int delete(Application application);

    int deleteAll();
}
