package rassvet.team.hire.dao;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rassvet.team.hire.dao.interfaces.ApplicationDao;
import rassvet.team.hire.mapper.ApplicationRowMapper;
import rassvet.team.hire.models.Application;
import rassvet.team.hire.models.enums.ApplicationStatus;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ApplicationJdbcTemplate implements ApplicationDao {
    private final JdbcTemplate jdbcTemplate;
    private final ApplicationRowMapper applicationMapper;

    @Override
    public Optional<Application> findById(Long id) {
        String query = "SELECT * FROM application WHERE id=?";
        return jdbcTemplate
                .query(query, applicationMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public Set<Application> findAllByTelegramId(Long telegramId) {
        String query = "SELECT * FROM application WHERE telegram_id=?";
        return new HashSet<>(jdbcTemplate.query(query, applicationMapper, telegramId));
    }

    @Override
    public Set<Application> findAll() {
        String query = "SELECT * FROM application";
        return new HashSet<>(jdbcTemplate.query(query, applicationMapper));
    }

    @Override
    public Map<String, String> findAnswersToQuestions(Long applicationId) {
        String query = "SELECT vq.question, aa.answer_text " +
                "FROM application_answers aa " +
                "JOIN vacancy_question vq ON aa.question_id = vq.id " +
                "WHERE aa.application_id=?";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, applicationId);
        Map<String, String> questionsAndAnswers = new HashMap<>();

        for (Map<String, Object> row : rows) {
            String questionText = (String) row.get("question_text");
            String answerText = (String) row.get("answer_text");
            questionsAndAnswers.put(questionText, answerText);
        }
        return questionsAndAnswers;
    }
    @Override
    public Application save(Application application) {
        if (Objects.isNull(application)) throw new IllegalArgumentException("Application is Null!");

        String query = "INSERT INTO application " +
                "(application_status,telegram_id,full_name,age,phone_number,contact_method,experience,vacancy_id)" +
                " VALUES(?,?,?,?,?,?,?,?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setString(index++, application.getApplicationStatus().getValue());
            ps.setString(index++, application.getTelegramId());
            ps.setString(index++, application.getFullName());
            ps.setInt(index++, application.getAge());
            ps.setString(index++, application.getPhoneNumber());
            ps.setString(index++, application.getContactMethod().getValue());
            ps.setString(index++, application.getExperience());
            ps.setLong(index++, application.getVacancy().getId());
            return ps;
        }, keyHolder);

        Long applicationId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application doesn't exist"));
    }

    @Override
    public Application update(Application application) {
        if (Objects.isNull(application)) throw new IllegalArgumentException("Application is Null!");
        Long id = application.getId();
        return updateById(id, application);
    }

    @Override
    public Application updateById(Long id, Application application) {
        if (Objects.isNull(application)) throw new IllegalArgumentException("Application is Null!");
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "UPDATE application SET " +
                    "application_status=?,telegram_id=?,full_name=?,age=?,phone_number=?," +
                    "contact_method=?,experience=?,vacancy_id=? " +
                    "WHERE id=?";
            int rows = jdbcTemplate.update(query, application.getApplicationStatus().getValue(),
                    application.getTelegramId(), application.getFullName(), application.getAge(),
                    application.getPhoneNumber(), application.getContactMethod().getValue(),
                    application.getExperience(), application.getVacancy().getId(), id);
            if (rows != 1) {
                throw new RuntimeException("Invalid request in SQL: " + query);
            }
            return findById(id).get();
        } else {
            throw new NotFoundException(String.format("Application with id %d wasn't found", id));
        }
    }

    @Override
    public int deleteById(Long id) {
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "DELETE FROM application WHERE id=?";
            return jdbcTemplate.update(query, id);
        } else {
            throw new NotFoundException(String.format("Application with id %d wasn't found", id));
        }
    }

    @Override
    public int delete(Application application) {
        if (Objects.isNull(application)) throw new IllegalArgumentException("Application is Null!");
        Long id = application.getId();
        return deleteById(id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM application";
        return jdbcTemplate.update(query);
    }

    @Override
    public Set<Application> findAllByVacancyId(Long vacancyId) {
        String query = "SELECT * FROM application WHERE vacancy_id=?";
        return new HashSet<>(jdbcTemplate.query(query, applicationMapper, vacancyId));
    }

    @Override
    public Set<Application> findAllByStatus(ApplicationStatus applicationStatus) {
        String query = "SELECT * FROM application WHERE application_status=?";
        return new HashSet<>(jdbcTemplate.query(query, applicationMapper, applicationStatus.getValue()));
    }

    @Override
    public Set<Application> findAllByVacancyIdAndStatus(Long vacancyId, ApplicationStatus applicationStatus) {
        String query = "SELECT * FROM application WHERE vacancy_id=? AND application_status=?";
        return new HashSet<>(jdbcTemplate.query(query, applicationMapper, vacancyId, applicationStatus.getValue()));
    }

}
