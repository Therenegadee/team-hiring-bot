package rassvet.team.hire.dao;

import org.springframework.stereotype.Component;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.Vacancy;

import java.util.Optional;
import java.util.Set;

@Component
public class VacancyJdbcTemplate implements VacancyDao {
    @Override
    public Optional<Vacancy> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Vacancy> findByPositionName(String positionName) {
        return Optional.empty();
    }

    @Override
    public Set<Vacancy> findAll() {
        return null;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return null;
    }

    @Override
    public Vacancy update(Vacancy vacancy) {
        return null;
    }

    @Override
    public Vacancy updateById(Long id) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int delete(Vacancy vacancy) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}
