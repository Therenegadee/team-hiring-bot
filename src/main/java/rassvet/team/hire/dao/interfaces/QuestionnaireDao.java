package rassvet.team.hire.dao.interfaces;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.models.Questionnaire;

import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionnaireDao {
    Optional<Questionnaire> findById(Long id);
    Optional<Questionnaire> findByPosition(String position);
    Set<Questionnaire> findAll();
    Questionnaire save(Questionnaire questionnaire);
    Questionnaire update(Questionnaire questionnaire);
    Questionnaire updateById(Long id);
    int deleteById(Long id);
    int delete(Questionnaire questionnaire);
    int deleteAll();
}
