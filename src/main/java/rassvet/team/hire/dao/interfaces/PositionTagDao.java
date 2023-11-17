package rassvet.team.hire.dao.interfaces;

import org.springframework.stereotype.Repository;
import rassvet.team.hire.models.PositionTag;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PositionTagDao {
    Optional<PositionTag> findById(Long id);

    Optional<PositionTag> findByTagName(String tagName);

    Set<PositionTag> findAll();

    Set<PositionTag> findAllByRoleId(Long roleId);

    PositionTag save(PositionTag positionTag);

    PositionTag update(PositionTag positionTag);

    PositionTag updateById(Long id, PositionTag positionTag);

    int deleteById(Long id);

    int delete(PositionTag positionTag);

    int deleteAll();
}
