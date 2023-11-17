package rassvet.team.hire.dao;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.dao.interfaces.RoleDao;
import rassvet.team.hire.models.PositionTag;
import rassvet.team.hire.models.Role;

import java.util.Optional;
import java.util.Set;

@Repository
public class RoleJdbcTemplate implements RoleDao {
    @Override
    public Optional<Role> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByUserId(Long userId){
        return Optional.empty();
    }

    @Override
    public Set<Role> findAll() {
        return null;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public Role updateById(Long id, Role role) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int delete(Role role) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}
