package rassvet.team.hire.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Table("vacancy")
@Component
public class Vacancy {
    @Id
    private Long id;
    private PositionTag positionTag;
    private String positionName;
    private String description;
    private List<String> questions;
}
