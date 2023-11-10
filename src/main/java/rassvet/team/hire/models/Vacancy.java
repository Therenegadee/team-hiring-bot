package rassvet.team.hire.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Vacancy {
    @Id
    private Long id;
    private String positionName;
    private List<String> questions;
}
