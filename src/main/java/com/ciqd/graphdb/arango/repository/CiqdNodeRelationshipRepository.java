package com.ciqd.graphdb.arango.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.ciqd.graphdb.arango.domain.CiqdNodeRelationship;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface CiqdNodeRelationshipRepository extends ArangoRepository<CiqdNodeRelationship, String> {

}
