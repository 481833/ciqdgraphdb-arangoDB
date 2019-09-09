package com.ciqd.graphdb.arango.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.ciqd.graphdb.arango.domain.CiqdNodeRelationship;
import org.springframework.stereotype.Repository;

public interface CiqdNodeRelationshipRepository extends ArangoRepository<CiqdNodeRelationship, String> {

}
