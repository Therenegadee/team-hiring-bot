package rassvet.team.hire.dao.interfaces;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.models.Vacancy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public interface VacancyDao {
    Optional<Vacancy> findById(Long id);

    Optional<Vacancy> findByPositionName(String positionName);

    Set<Vacancy> findAll();

    Vacancy save(Vacancy vacancy);

    Vacancy update(Vacancy vacancy);

    Vacancy updateById(Long id, Vacancy vacancy);

    int deleteById(Long id);

    int delete(Vacancy vacancy);

    int deleteAll();

    List<Long> findQuestionsIdsByVacancyId(Long vacancyId);

    List<String> findQuestionsByVacancyId(Long vacancyId);

    Map<Long, String> findQuestionsWithIdsByVacancyId(Long vacancyId);
}
