package rassvet.team.hire.dao;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rassvet.team.hire.dao.interfaces.RoleDao;
import rassvet.team.hire.mapper.RoleRowMapper;
import rassvet.team.hire.models.PositionTag;
import rassvet.team.hire.models.Role;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RoleJdbcTemplate implements RoleDao {
    private final JdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleMapper;
    @Override
    public Optional<Role> findById(Long id) {
        String query = "SELECT * FROM roles WHERE id=?";
        return jdbcTemplate
                .query(query, roleMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        String query = "SELECT * FROM roles WHERE role_name=?";
        return jdbcTemplate
                .query(query, roleMapper, roleName)
                .stream()
                .findAny();
    }

    @Override
    public Optional<Role> findByUserId(Long userId){
        String query = "SELECT * FROM roles WHERE id IN (SELECT * FROM users_roles WHERE user_id=?)";
        return jdbcTemplate
                .query(query, roleMapper, userId)
                .stream()
                .findAny();
    }

    @Override
    public Set<Role> findAll() {
        String query = "SELECT * FROM roles";
        return new HashSet<>(jdbcTemplate.query(query, roleMapper));
    }

    @Override
    public Role save(Role role) {
        if (Objects.isNull(role)) throw new IllegalArgumentException("Role is Null!");

        String query = "INSERT INTO roles (role_name) VALUES(?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setString(index, role.getRoleName());
            return ps;
        }, keyHolder);

        if(!role.getPositionTagsAllowedToModerate().isEmpty()){
            savePositionTagsAllowedToModerate(role);
        }
        Long roleId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role doesn't exist"));
    }

    private void savePositionTagsAllowedToModerate(Role role) {
        String query = "INSERT INTO roles_position_tags (role_id, position_tag_id) VALUES(?,?)";
        Long roleId = role.getId();
        Set<PositionTag> positionTagsAllowedToModerate = role.getPositionTagsAllowedToModerate();
        for(PositionTag positionTag : positionTagsAllowedToModerate) {
            jdbcTemplate.update(query, roleId, positionTag.getId());
        }
    }

    @Override
    public Role update(Role role) {
        if (Objects.isNull(role)) throw new IllegalArgumentException("Role is Null!");
        Long id = role.getId();
        return updateById(id, role);
    }

    @Override
    public Role updateById(Long id, Role role) {
        if (Objects.isNull(role)) throw new IllegalArgumentException("Role is Null!");
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "UPDATE roles SET role_name=? WHERE id=?";
            int rows = jdbcTemplate.update(query, role.getRoleName(), id);
            if (rows != 1) {
                throw new RuntimeException("Invalid request in SQL: " + query);
            }
            return findById(id).get();
        } else {
            throw new NotFoundException(String.format("Role with id %d wasn't found", id));
        }
    }

    @Override
    public int deleteById(Long id) {
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "DELETE FROM roles WHERE id=?";
            return jdbcTemplate.update(query, id);
        } else {
            throw new NotFoundException(String.format("Role with id %d wasn't found", id));
        }
    }

    @Override
    public int delete(Role role) {
        if (Objects.isNull(role)) throw new IllegalArgumentException("Role is Null!");
        Long id = role.getId();
        return deleteById(id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM roles";
        return jdbcTemplate.update(query);
    }
}
