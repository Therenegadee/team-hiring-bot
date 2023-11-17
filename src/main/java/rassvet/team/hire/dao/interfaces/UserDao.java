package rassvet.team.hire.dao.interfaces;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.models.Role;
import rassvet.team.hire.models.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserDao {
    Optional<User> findById(Long id);
    Optional<User> findByTelegramId(Long telegramId);
    boolean existsByTelegramId(Long telegramId);
    Set<User> findAll();
    Set<User> findAllByRole(Role role);
    User save(User user);
    User update(User user);
    User updateById(Long id, User user);
    int deleteById(Long id);
    int delete(User user);
    int deleteAll();
}
