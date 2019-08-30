package com.ciqd.graphdb.arango.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.ciqd.graphdb.arango.domain.CiqdEdge;

public interface CiqdEdgeRepository extends ArangoRepository<CiqdEdge, String> {
}
