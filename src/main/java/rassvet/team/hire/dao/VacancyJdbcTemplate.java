package rassvet.team.hire.dao;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rassvet.team.hire.dao.interfaces.VacancyDao;
import rassvet.team.hire.mapper.VacancyRowMapper;
import rassvet.team.hire.models.Vacancy;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class VacancyJdbcTemplate implements VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final VacancyRowMapper vacancyRowMapper;

    @Override
    public Optional<Vacancy> findById(Long id) {
        String query = "SELECT * FROM vacancy WHERE id=?";
        return jdbcTemplate
                .query(query, vacancyRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public Optional<Vacancy> findByPositionName(String positionName) {
        String query = "SELECT * FROM vacancy WHERE id=?";
        return jdbcTemplate
                .query(query, vacancyRowMapper, positionName)
                .stream()
                .findAny();
    }

    @Override
    public Set<Vacancy> findAll() {
        String query = "SELECT * FROM vacancy";
        return new HashSet<>(jdbcTemplate.query(query, vacancyRowMapper));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        if (Objects.isNull(vacancy)) throw new IllegalArgumentException("Vacancy is Null!");

        String queryToVacancyTable = "INSERT INTO vacancy " +
                "(position_name) " +
                "VALUES(?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(queryToVacancyTable, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setString(index++, vacancy.getPositionName());
            return ps;
        }, keyHolder);
        Long vacancyId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        List<String> questions = vacancy.getQuestions();

        Object[] batchArgs = questions.stream()
                .map(question -> new Object[]{question, vacancyId})
                .toArray();

        String queryToVacancyQuestionsTable = "INSERT INTO vacancy_questions" +
                "(question_text,vacancy_id) " +
                "VALUES (?,?)";
        jdbcTemplate.update(queryToVacancyQuestionsTable, batchArgs);
        return findById(vacancyId)
                .orElseThrow(() -> new RuntimeException("Vacancy doesn't exist"));
    }

    @Override
    public Vacancy update(Vacancy vacancy) {
        if (Objects.isNull(vacancy)) throw new IllegalArgumentException("Vacancy is Null!");
        Long id = vacancy.getId();
        return updateById(id, vacancy);
    }

    @Override
    public Vacancy updateById(Long id, Vacancy vacancy) {
        if (Objects.isNull(vacancy)) throw new IllegalArgumentException("Vacancy is Null!");
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String queryToVacancyTable = "UPDATE vacancy SET position_name=? WHERE id=?";
            jdbcTemplate.update(queryToVacancyTable, vacancy.getPositionName(), id);

            jdbcTemplate.update("DELETE FROM vacancy_questions WHERE vacancy_id?", id);

            List<String> questions = vacancy.getQuestions();
            Object[] batchArgs = questions.stream()
                    .map(question -> new Object[]{question, id})
                    .toArray();
            String queryToVacancyQuestionsTable = "INSERT INTO vacancy_questions" +
                    "(question_text,vacancy_id) " +
                    "VALUES (?,?)";
            jdbcTemplate.update(queryToVacancyQuestionsTable, batchArgs);

            return findById(id).get();
        } else {
            throw new NotFoundException(String.format("Vacancy with id %d wasn't found", id));
        }
    }

    @Override
    public int deleteById(Long id) {
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String queryToVacancyTable = "DELETE FROM vacancy WHERE id=?";
            String queryToVacancyQuestionsTable = "DELETE FROM vacancy_questions WHERE vacancy_id?";
            jdbcTemplate.update(queryToVacancyQuestionsTable, id);
            return jdbcTemplate.update(queryToVacancyTable, id);
        } else {
            throw new NotFoundException(String.format("Vacancy with id %d wasn't found", id));
        }
    }

    @Override
    public int delete(Vacancy vacancy) {
        if (Objects.isNull(vacancy)) throw new IllegalArgumentException("Vacancy is Null!");
        Long id = vacancy.getId();
        return deleteById(id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM vacancy";
        return jdbcTemplate.update(query);
    }
    @Override
    public List<Long> findQuestionsIdsByVacancyId(Long vacancyId) {
        String query = "SELECT id FROM vacancy_questions WHERE vacancy_id=?";
        return jdbcTemplate.queryForList(query, Long.class, vacancyId);
    }

    @Override
    public List<String> findQuestionsByVacancyId(Long vacancyId) {
        String query = "SELECT question_text FROM vacancy_questions WHERE vacancy_id=?";
        return jdbcTemplate.queryForList(query, String.class, vacancyId);
    }

    @Override
    public Map<Long, String> findQuestionsWithIdsByVacancyId(Long vacancyId) {
        String query = "SELECT id, question_text FROM vacancy_questions WHERE vacancy_id=?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, vacancyId);
        Map<Long, String> questionMap = new HashMap<>();

        for(Map<String, Object> row: rows) {
            Long questionId = (Long) row.get("id");
            String questionText = (String) row.get("question_text");
            questionMap.put(questionId, questionText);
        }
        return questionMap;
    }
}
