package rassvet.team.hire.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rassvet.team.hire.dao.interfaces.RoleDao;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class UserRowMapper implements RowMapper<User> {
    private final RoleDao roleDao;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long userId = rs.getLong("id");
        Role role = findRoleByUserId(userId);
        return User.builder()
                .id(userId)
                .role(role)
                .telegramId(rs.getString("telegram_id"))
                .username(rs.getString("username"))
                .phoneNumber(rs.getString("phone_number"))
                .fullName(rs.getString("full_name"))
                .secretKey(rs.getString("secret_key"))
                .build();
    }

    //TODO: добавить обработку исключения
    private Role findRoleByUserId(Long userId) {
        return roleDao.findByUserId(userId)
                .orElseThrow(RuntimeException::new);
    }
}
