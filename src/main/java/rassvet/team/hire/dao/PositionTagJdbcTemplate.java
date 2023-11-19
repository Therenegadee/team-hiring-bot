package rassvet.team.hire.dao;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rassvet.team.hire.dao.interfaces.PositionTagDao;
import rassvet.team.hire.mapper.PositionTagRowMapper;
import rassvet.team.hire.models.PositionTag;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PositionTagJdbcTemplate implements PositionTagDao {
    private final JdbcTemplate jdbcTemplate;
    private final PositionTagRowMapper positionTagMapper;
    @Override
    public Optional<PositionTag> findById(Long id) {
        String queryToPositionTags = "SELECT * FROM position_tags WHERE id=?";
        return jdbcTemplate.query(queryToPositionTags, positionTagMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public Optional<PositionTag> findByTagName(String tagName) {
        String query = "SELECT * FROM position_tags WHERE tag_name=?";
        return jdbcTemplate.query(query, positionTagMapper, tagName)
                .stream()
                .findAny();
    }

    @Override
    public Set<PositionTag> findAll() {
        String query = "SELECT * FROM position_tags";
        return new HashSet<>(jdbcTemplate.query(query, positionTagMapper));
    }

    @Override
    public Set<PositionTag> findAllByRoleId(Long roleId) {
        String query = "SELECT * FROM roles_position_tags WHERE role_id=?";
        return new HashSet<>(jdbcTemplate.query(query, positionTagMapper));
    }

    @Override
    public PositionTag save(PositionTag positionTag) {
        if (Objects.isNull(positionTag)) throw new IllegalArgumentException("Position Tag is Null!");

        String query = "INSERT INTO position_tags (tag_name) VALUES(?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setString(index, positionTag.getTagName());
            return ps;
        }, keyHolder);

        Long positionTagId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(positionTagId)
                .orElseThrow(() -> new RuntimeException("Position Tag doesn't exist"));
    }

    @Override
    public PositionTag update(PositionTag positionTag) {
        if (Objects.isNull(positionTag)) throw new IllegalArgumentException("Position Tag is Null!");
        Long id = positionTag.getId();
        return updateById(id, positionTag);
    }

    @Override
    public PositionTag updateById(Long id, PositionTag positionTag) {
        if (Objects.isNull(positionTag)) throw new IllegalArgumentException("Position Tag is Null!");
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "UPDATE position_tags SET tag_name=? WHERE id=?";
            int rows = jdbcTemplate.update(query, positionTag.getTagName(), id);
            if (rows != 1) {
                throw new RuntimeException("Invalid request in SQL: " + query);
            }
            return findById(id).get();
        } else {
            throw new NotFoundException(String.format("Position Tag with id %d wasn't found", id));
        }
    }

    @Override
    public int deleteById(Long id) {
        if (Objects.nonNull(id) && findById(id).isPresent()) {
            String query = "DELETE FROM position_tags WHERE id=?";
            return jdbcTemplate.update(query, id);
        } else {
            throw new NotFoundException(String.format("Position Tag with id %d wasn't found", id));
        }
    }

    @Override
    public int delete(PositionTag positionTag) {
        if (Objects.isNull(positionTag)) throw new IllegalArgumentException("Position Tag is Null!");
        Long id = positionTag.getId();
        return deleteById(id);
    }

    @Override
    public int deleteAll() {
        String query = "DELETE FROM position_tags";
        return jdbcTemplate.update(query);
    }
}
