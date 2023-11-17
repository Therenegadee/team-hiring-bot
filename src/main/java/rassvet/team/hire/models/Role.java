package rassvet.team.hire.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@Builder
@Table("roles")
@Component
public class Role {
    private Long id;
    private String roleName;
    private Set<PositionTag> positionTagsAllowedToModerate;
}
