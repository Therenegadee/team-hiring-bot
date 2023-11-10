package rassvet.team.hire.dao.interfaces;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.models.Vacancy;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VacancyDao {
    Optional<Vacancy> findById(Long id);
    Optional<Vacancy> findByPositionName(String positionName);
    Set<Vacancy> findAll();
    Vacancy save(Vacancy vacancy);
    Vacancy update(Vacancy vacancy);
    Vacancy updateById(Long id);
    int deleteById(Long id);
    int delete(Vacancy vacancy);
    int deleteAll();
}
