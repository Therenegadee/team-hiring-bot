package rassvet.team.hire.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Answer {
    @Id
    private Long id;
    private String answer;
    private Question question; //many to one
}
