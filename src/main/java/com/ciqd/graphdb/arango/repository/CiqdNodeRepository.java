package com.ciqd.graphdb.arango.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.ciqd.graphdb.arango.domain.CiqdNode;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CiqdNodeRepository extends ArangoRepository<CiqdNode, String> {

    Optional<CiqdNode> findByNodeNameAndNodeType(String nodeName, String nodeType);
    Iterable<CiqdNode> findByRelationNodesNodeName(String name);


}
