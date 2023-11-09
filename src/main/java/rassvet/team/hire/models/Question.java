package rassvet.team.hire.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Question {
    private Long id;
    private String questionText;
    private List<Answer> answers;
}
