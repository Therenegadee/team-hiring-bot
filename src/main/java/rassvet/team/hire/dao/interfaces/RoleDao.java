package rassvet.team.hire.dao.interfaces;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.models.Role;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleDao {
    Optional<Role> findById(Long id);

    Optional<Role> findByRoleName(String roleName);

    Optional<Role> findByUserId(Long userId);

    Set<Role> findAll();

    Role save(Role role);

    Role update(Role role);

    Role updateById(Long id, Role role);

    int deleteById(Long id);

    int delete(Role role);

    int deleteAll();
}
