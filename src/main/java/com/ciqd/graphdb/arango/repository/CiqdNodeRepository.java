package com.ciqd.graphdb.arango.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.ciqd.graphdb.arango.domain.CiqdNode;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


public interface CiqdNodeRepository extends ArangoRepository<CiqdNode, String> {

    Optional<CiqdNode> findByNodeNameAndNodeType(String nodeName, String nodeType);
    Iterable<CiqdNode> findByRelationNodesNodeName(String name);

    @Query("FOR c IN ciqdnodes FILTER c.nodename == @nodeName SORT c.nodeName ASC RETURN c")
    Iterable<Character> getWithNodeName(@Param("nodename") String value);

    @Query("FOR v IN 1..2 INBOUND @id @@edgeCol SORT v.nodeName DESC RETURN DISTINCT v")
    Set<CiqdNode> getAllChildNodesAndGrandchildNodes(@Param("id") String id, @Param("@edgeCol") Class<?> edgeCollection);

}
