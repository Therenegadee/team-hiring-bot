package rassvet.team.hire.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rassvet.team.hire.dao.interfaces.PositionTagDao;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.PositionTag;
import rassvet.team.hire.models.Vacancy;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class VacancyRowMapper implements RowMapper<Vacancy> {
    private final VacancyDao vacancyDao;
    private final PositionTagDao positionTagDao;

    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long vacancyId = rs.getLong("id");
        Long positionTagId = rs.getLong("position_tag_id");
        //TODO: добавить обработку исключения
        PositionTag positionTag = positionTagDao.findById(positionTagId)
                .orElseThrow(RuntimeException::new);
        return Vacancy.builder()
                .id(vacancyId)
                .positionTag(positionTag)
                .positionName(rs.getString("position_name"))
                .description(rs.getString("description"))
                .questions(vacancyDao.findQuestionsByVacancyId(vacancyId))
                .build();
    }
}
