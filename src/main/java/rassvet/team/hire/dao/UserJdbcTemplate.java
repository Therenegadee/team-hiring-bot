package rassvet.team.hire.dao;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.mapper.UserRowMapper;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

//TODO: исправить добавление роли!!!
@Repository
@RequiredArgsConstructor
public class UserJdbcTemplate implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userMapper;

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id=?";
        return jdbcTemplate
                .query(query, userMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public Optional<User> findByTelegramId(Long telegramId) {
        String query = "SELECT * FROM users WHERE telegram_id=?";
        return jdbcTemplate
                .query(query, userMapper, telegramId)
                .stream()
                .findAny();
    }

    @Override
    public boolean existsByTelegramId(Long telegramId) {
        return findByTelegramId(telegramId).isPresent();
    }

    @Override
    public Set<User> findAll() {
        String query = "SELECT * FROM users";
        return new HashSet<>(jdbcTemplate.query(query, userMapper));
    }

    @Override
    public Set<User> findAllByRole(Role role) {
        String query = "SELECT * FROM users WHERE id IN (SELECT user_id FROM users_roles WHERE role_id = ?)";
        return new HashSet<>(jdbcTemplate.query(query, userMapper, role));
    }

    @Override
    public User save(User user) {
        if (Objects.isNull(user)) throw new IllegalArgumentException("User is Null!");

        String query = "INSERT INTO users " +
                "(telegram_id,username,phone_number,full_name,secret_key) " +
                "VALUES(?,?,?,?,?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setString(index++, user.getTelegramId());
            ps.setString(index++, user.getUsername());
            ps.setString(index++, user.getPhoneNumber());
            ps.setString(index++, user.getFullName());
            ps.setString(index++, user.getSecretKey());
            return ps;
        }, keyHolder);

        Long userId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        saveUserRole(userId, user.getRole().getId());

        return findById(userId)
                .orElseThrow(() -> new RuntimeException("User doesn't exist"));
    }

    private void saveUserRole(Long userId, Long roleId) {
        String query = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        jdbcTemplate.update(query, userId, roleId);
    }

    @Override
    public User update(User user) {
        if (Objects.isNull(user)) throw new IllegalArgumentException("User is Null!");
        Long id = user.getId();
        return updateById(id, user);
    }

    @Override
    public User updateById(Long id, User user) {
        if (Objects.isNull(user)) throw new IllegalArgumentException("User is Null!");
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "UPDATE users SET " +
                    "role=?,telegram_id=?,username=?,phone_number=?,full_name=?,secret_key=?" +
                    " WHERE id=?";
            int rows = jdbcTemplate.update(query, user.getRole(), user.getTelegramId(),
                    user.getUsername(), user.getPhoneNumber(), user.getFullName(), user.getSecretKey(), id);
            if (rows != 1) {
                throw new RuntimeException("Invalid request in SQL: " + query);
            }
            return findById(id).get();
        } else {
            throw new NotFoundException(String.format("User with id %d wasn't found", id));
        }
    }

    @Override
    public int deleteById(Long id) {
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "DELETE FROM users WHERE id=?";
            return jdbcTemplate.update(query, id);
        } else {
            throw new NotFoundException(String.format("User with id %d wasn't found", id));
        }
    }

    @Override
    public int delete(User user) {
        if (Objects.isNull(user)) throw new IllegalArgumentException("User is Null!");
        Long id = user.getId();
        return deleteById(id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM users";
        return jdbcTemplate.update(query);
    }
}
