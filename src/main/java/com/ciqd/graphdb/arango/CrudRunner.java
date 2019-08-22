package com.ciqd.graphdb.arango;


import com.ciqd.graphdb.arango.domain.CiqdNode;
import com.ciqd.graphdb.arango.domain.CiqdNodeRelationship;
import com.ciqd.graphdb.arango.repository.CiqdNodeRelationshipRepository;
import com.ciqd.graphdb.arango.repository.CiqdNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import com.arangodb.springframework.core.ArangoOperations;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import java.util.Arrays;
import java.util.Collection;


@ComponentScan("com.ciqd.graphdb.arango")
public class CrudRunner implements CommandLineRunner {

    @Autowired
    private CiqdNodeRepository ciqdNodeRepository;

    @Autowired
    private ArangoOperations operations;

    @Autowired
    private CiqdNodeRelationshipRepository ciqdNodeRelationshipRepository;


    @Override
    public void run(final String... args) throws Exception {
       System.out.println("Create Node Type");
       operations.dropDatabase();

       Collection<CiqdNode> ciqdNodes = createNodes();
       ciqdNodeRepository.saveAll(ciqdNodes);

       Iterable<CiqdNode> allCiqdNodes = ciqdNodeRepository.findAll();

       long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(allCiqdNodes.iterator(), 0), false).count();

       System.out.println(String.format("Ciqd node saved in the database : '%s'", count));

       // Create relationship between Start Node and Test step Node

       ciqdNodeRepository.findByNodeNameAndNodeType("S", "START_NODE").ifPresent(startNode ->
            {
            ciqdNodeRepository.findByNodeNameAndNodeType("T1", "TESTSTEP_NODE").ifPresent(testStepNode ->
                    {
                         ciqdNodeRelationshipRepository.save(new CiqdNodeRelationship(startNode, testStepNode));
                    }
                );
            }
       );

        // after we add @Relations(edges= CiqdNodeRelationship.class, lazy = true) in CiqdNode
        // we can now load all children of a CiqdNode when we fetch the node
        ciqdNodeRepository.findByNodeNameAndNodeType("S", "START_NODE").ifPresent(startnode -> {
            System.out.println(String.format("## These are the children of %s:", startnode.getNodeName()));
            startnode.getRelationNodes().forEach((n) -> System.out.println(n.getNodeName()));
        });

        // the fields 'relationNodes' isn't persisted in the CiqdNode document itself, it's represented through
        // the edges. Nevertheless we can write a derived method which includes properties of the connected nodes
        System.out.println("## These are the parents of test step node");
        final Iterable<CiqdNode> parentsOfTestStepNode = ciqdNodeRepository.findByRelationNodesNodeName("T1");
        parentsOfTestStepNode.forEach((n) -> System.out.println(n.getNodeName()));

    }

    public static Collection<CiqdNode> createNodes() {
       //final CiqdNode startNode = new CiqdNode("S","START_NODE");
       //return Arrays.asList(startNode);
        return Arrays.asList(new CiqdNode("S","START_NODE"), new CiqdNode("T1","TESTSTEP_NODE"));
    }
}

