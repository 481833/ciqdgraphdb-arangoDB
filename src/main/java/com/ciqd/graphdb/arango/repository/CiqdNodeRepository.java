package com.ciqd.graphdb.arango.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.ciqd.graphdb.arango.domain.CiqdNode;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


public interface CiqdNodeRepository extends ArangoRepository<CiqdNode, String> {

    Optional<CiqdNode> findByOldId(String oldId);
    Optional<CiqdNode> findByType(String type);
    Iterable<CiqdNode> findByRelationNodesType(String type);

    @Query("FOR c IN nodes FILTER c.oldId == @oldId RETURN c")
    Iterable<CiqdNode> getWithOldId(@Param("oldId") String id);

    @Query("FOR v IN 1..2 INBOUND @id @@edgeCol RETURN v")
    Set<CiqdNode> getAllChildNodesAndGrandchildNodes(@Param("id") String id, @Param("@edgeCol") Class<?> edgeCollection);

}
