package rassvet.team.hire.dao;

import rassvet.team.hire.dao.interfaces.UserDao;
import rassvet.team.hire.models.User;
import rassvet.team.hire.models.enums.Role;

import java.util.Optional;
import java.util.Set;

public class UserJdbcTemplate implements UserDao {
    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByTelegramId(Long telegramId) {
        return Optional.empty();
    }

    @Override
    public boolean existsByTelegramId(Long telegramId) {
        return false;
    }

    @Override
    public Set<User> findAll() {
        return null;
    }

    @Override
    public Set<User> findAllByRole(Role role) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User updateById(Long id) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}
