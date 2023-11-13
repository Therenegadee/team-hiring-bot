package rassvet.team.hire.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.Vacancy;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class VacancyRowMapper implements RowMapper<Vacancy> {
    private final VacancyDao vacancyDao;

    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long vacancyId = rs.getLong("id");
        return Vacancy.builder()
                .id(vacancyId)
                .positionName(rs.getString("position_name"))
                .questions(vacancyDao.findQuestionsByVacancyId(vacancyId))
                .build();
    }
}
