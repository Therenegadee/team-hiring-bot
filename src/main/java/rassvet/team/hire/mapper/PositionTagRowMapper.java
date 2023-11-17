package rassvet.team.hire.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rassvet.team.hire.models.PositionTag;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PositionTagRowMapper implements RowMapper<PositionTag> {
    @Override
    public PositionTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PositionTag.builder()
                .id(rs.getLong("id"))
                .tagName(rs.getString("tag_name"))
                .build();
    }
}
