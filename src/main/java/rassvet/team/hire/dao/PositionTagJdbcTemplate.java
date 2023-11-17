package rassvet.team.hire.dao;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.dao.interfaces.PositionTagDao;
import rassvet.team.hire.models.PositionTag;

import java.util.Optional;
import java.util.Set;

@Repository
public class PositionTagJdbcTemplate implements PositionTagDao {
    @Override
    public Optional<PositionTag> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<PositionTag> findByTagName(String tagName) {
        return Optional.empty();
    }

    @Override
    public Set<PositionTag> findAll() {
        return null;
    }

    @Override
    public Set<PositionTag> findAllByRoleId(Long roleId) {
        return null;
    }

    @Override
    public PositionTag save(PositionTag positionTag) {
        return null;
    }

    @Override
    public PositionTag update(PositionTag positionTag) {
        return null;
    }

    @Override
    public PositionTag updateById(Long id, PositionTag positionTag) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int delete(PositionTag positionTag) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}
