package rassvet.team.hire.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rassvet.team.hire.dao.interfaces.PositionTagDao;
import rassvet.team.hire.models.PositionTag;
import rassvet.team.hire.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleRowMapper implements RowMapper<Role> {
    private final PositionTagDao positionTagDao;

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long roleId = rs.getLong("id");
        Set<PositionTag> positionTagsAllowedToModerate = positionTagDao.findAllByRoleId(roleId);
        return Role.builder()
                .id(roleId)
                .roleName(rs.getString("role_name"))
                .positionTagsAllowedToModerate(positionTagsAllowedToModerate)
                .build();
    }
}
