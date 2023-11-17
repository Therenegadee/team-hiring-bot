package rassvet.team.hire.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@Table(name = "position_tags")
@Component
public class PositionTag {
    private Long id;
    private String tagName;
}
