package com.ciqd.graphdb.arango;

import com.arangodb.springframework.core.ArangoOperations;
import com.ciqd.graphdb.arango.domain.CiqdNode;
import com.ciqd.graphdb.arango.domain.CiqdNodeRelationship;
import com.ciqd.graphdb.arango.repository.CiqdNodeRelationshipRepository;
import com.ciqd.graphdb.arango.repository.CiqdNodeRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

import static com.ciqd.graphdb.arango.CrudRunner.createNodes;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.ciqd.graphdb.arango")
public class CiqdNodeRepositoryTest {

        @Autowired
        private CiqdNodeRepository ciqdNodeRepository;

        @Autowired
        private ArangoOperations operations;

        @Autowired
        private CiqdNodeRelationshipRepository ciqdNodeRelationshipRepository;

        @Before
        public void setUp() {
                operations.dropDatabase();
                Collection<CiqdNode> ciqdNodes = createNodes();
                ciqdNodeRepository.saveAll(ciqdNodes);


        }

        @Test
        public void testCreateRelations() {
                ciqdNodeRepository.findByNodeNameAndNodeType("S", "START_NODE").ifPresent(startNode ->
                        {
                                ciqdNodeRepository.findByNodeNameAndNodeType("T1", "TESTSTEP_NODE").ifPresent(testStepNode ->
                                        {
                                                ciqdNodeRelationshipRepository.save(new CiqdNodeRelationship(startNode, testStepNode));
                                        }
                                );
                        }
                );
        }

        @Ignore
        @Test
        public void testFindBy() {
                ciqdNodeRepository.findByNodeNameAndNodeType("S", "START_NODE").ifPresent(startnode -> {
                        System.out.println(String.format("## These are the children of %s:", startnode.getNodeName()));
                        startnode.getRelationNodes().forEach((n) -> System.out.println(n.getNodeName()));
                });

        }

        @Ignore
        @Test
        public void testRemoveBy() {

        }

        }
