package rassvet.team.hire.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rassvet.team.hire.dao.interfaces.ApplicationDao;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.Vacancy;
import rassvet.team.hire.models.enums.ApplicationStatus;
import rassvet.team.hire.models.enums.ContactMethod;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplicationRowMapper implements RowMapper<Application> {
    private final VacancyDao vacancyDao;
    private final ApplicationDao applicationDao;

    @Override
    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long applicationId = rs.getLong("id");
        Map<String, String> answersAndQuestion = applicationDao.findAnswersToQuestions(applicationId);
        return Application.builder()
                .id(applicationId)
                .applicationStatus(ApplicationStatus.fromValue(rs.getString("application_status")))
                .telegramId(rs.getString("telegram_id"))
                .fullName(rs.getString("full_name"))
                .age(rs.getInt("age"))
                .phoneNumber(rs.getString("phone_number"))
                .contactMethod(ContactMethod.fromValue(rs.getString("contact_method")))
                .experience(rs.getString("experience"))
                .vacancy(findVacancyById(rs.getLong("vacancy_id")))
                .answers(answersAndQuestion)
                .build();
    }

    //TODO: добавить обработку ошибки
    private Vacancy findVacancyById(Long id) {
        return vacancyDao.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
