package rassvet.team.hire.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .role(Role.fromValue(rs.getString("role")))
                .telegramId(rs.getString("telegram_id"))
                .username(rs.getString("username"))
                .phoneNumber(rs.getString("phone_number"))
                .fullName(rs.getString("full_name"))
                .secretKey(rs.getString("secret_key"))
                .build();
    }
}
