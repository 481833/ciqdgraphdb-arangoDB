package com.ciqd.graphdb.arango.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.ciqd.graphdb.arango.domain.CiqdNode;
import com.ciqd.graphdb.arango.domain.CiqdNodeRelationship;
import org.json.simple.JSONArray;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

public interface CiqdNodeRepository extends ArangoRepository<CiqdNode, String> {

    Optional<CiqdNode> findByOldId(String oldId);
    Optional<CiqdNode> findByType(String type);
    Iterable<CiqdNode> findByRelationNodesType(String type);

    @Query("FOR c IN nodes FILTER c.oldId == @oldId RETURN c")
    Iterable<CiqdNode> getWithOldId(@Param("oldId") String id);

    @Query("FOR v IN 1..100 OUTBOUND @id @@edgeCol RETURN v")
    Set<CiqdNode> getAllChildNodesAndGrandchildNodes(@Param("id") String id, @Param("@edgeCol") Class<?> edgeCollection);

    @Query("FOR v,e,p IN 1..100 ANY @id @@edgeCol RETURN TO_STRING(p.vertices[*]._key)")
    Collection<String> getVerticesForPaths(@Param("id") String id, @Param("@edgeCol") Class<?> edgeCollection);

    //@Query("FOR x IN INTERSECTION ((FOR v, e, p IN 1..100 OUTBOUND @id @@edgeCol RETURN p.vertices[*]._key), (FOR v, e, p IN 1..1000 INBOUND @id @@edgeCol RETURN p.vertices[*]._key ) ) RETURN TO_STRING(x)")
    @Query("FOR v,e,p IN 1..100 OUTBOUND @id @@edgeCol OPTIONS{uniqueEdges: 'path', uniqueVertices: 'none'} FILTER v._id == @id RETURN TO_STRING(p.vertices[*]._key)")
    Collection<String> getLoopPaths(@Param("id") String id, @Param("@edgeCol") Class<?> edgeCollection);

}
