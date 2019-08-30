package com.ciqd.graphdb.arango;


import com.ciqd.graphdb.arango.domain.CiqdEdge;
import com.ciqd.graphdb.arango.domain.CiqdNode;
import com.ciqd.graphdb.arango.domain.CiqdNodeRelationship;
import com.ciqd.graphdb.arango.repository.CiqdEdgeRepository;
import com.ciqd.graphdb.arango.repository.CiqdNodeRelationshipRepository;
import com.ciqd.graphdb.arango.repository.CiqdNodeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import com.arangodb.springframework.core.ArangoOperations;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import org.springframework.core.io.Resource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@ComponentScan("com.ciqd.graphdb.arango")
public class CrudRunner implements CommandLineRunner {

    @Autowired
    private CiqdNodeRepository ciqdNodeRepository;

    @Autowired
    private CiqdEdgeRepository ciqdEdgeRepository;

    @Autowired
    private ArangoOperations operations;

    @Autowired
    private CiqdNodeRelationshipRepository ciqdNodeRelationshipRepository;

    @Autowired
    ResourceLoader resourceLoader;

    ApplicationContext context;

    @Value("classpath:data/test1.json")
    Resource resource;

    private final static String startType = "ciqdelements.uml.Start";
    private final static String processingType = "ciqdelements.uml.Processing";
    private final static String decisionType = "ciqdelements.uml.Decision";
    private final static String finalType = "ciqdelements.uml.Final";
    private final static String edgeType = "ciqdelements.uml.Edge";

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("Create Node Type");
        operations.dropDatabase();

        Collection<Object> nodesAndRelationships = createNodesAndRelationships();

        Iterator iterator = nodesAndRelationships.iterator();
        List<CiqdNode> ciqdNodes=new ArrayList<>();
        List<CiqdEdge> ciqdEdges =new ArrayList<>();
        while (iterator.hasNext()) {
            Object entry= iterator.next();
            if (CiqdNode.class.equals(entry.getClass())) {
                ciqdNodes.add((CiqdNode) entry);
                } else if (CiqdEdge.class.equals(entry.getClass())) {
                    ciqdEdges.add((CiqdEdge)entry);
                }
        }


        ciqdNodeRepository.saveAll(ciqdNodes);
        ciqdEdgeRepository.saveAll(ciqdEdges);

        System.out.println(String.format("Ciqd node saved in the database : '%s'", ciqdNodeRepository.count()));
        System.out.println(String.format("Ciqd edges saved in the database : '%s'", ciqdEdgeRepository.count()));

        // Create relationships between nodes
        Iterable<CiqdEdge> ciqdEdgeIterable = ciqdEdgeRepository.findAll();
        Iterator edgesIterator = ciqdEdgeIterable.iterator();
        while (edgesIterator.hasNext()) {
           CiqdEdge ciqdEdge = (CiqdEdge) edgesIterator.next();
            ciqdNodeRepository.findByOldId(ciqdEdge.getSourceId()).ifPresent(srcNode -> {
                ciqdNodeRepository.findByOldId(ciqdEdge.getTargetId()).ifPresent(targetNode -> {
                    ciqdNodeRelationshipRepository.save(new CiqdNodeRelationship(targetNode,srcNode));
                });
            });
            }

       //after we add @Relations(edges= CiqdNodeRelationship.class, lazy = true) in CiqdNode
        // we can now load all children of a CiqdNode when we fetch the node
        ciqdNodeRepository.findByType(startType).ifPresent(startnode -> {
        System.out.println(String.format("## These are the children of %s:", startnode.getType()));
                startnode.getRelationNodes().forEach((n) -> System.out.println(n.getType())); });

        // the fields 'relationNodes' isn't persisted in the CiqdNode document itself, it's represented through
        // the edges. Nevertheless we can write a derived method which includes properties of the connected nodes
         System.out.println("## These are the parents of final node");
         final Iterable<CiqdNode> parentsONodeType = ciqdNodeRepository.findByRelationNodesType(finalType);
            parentsONodeType.forEach((n) -> System.out.println(n.getType()));

        System.out.println("## Find by Old Id with AQL query");
        ciqdNodeRepository.findByType(decisionType).ifPresent(decisionnode -> {
            final Iterable<CiqdNode> nodeFound = ciqdNodeRepository.getWithOldId(decisionnode.getOldId());
            nodeFound.forEach((n)->System.out.println(n.getType())); });

        System.out.println("## Find all childs and grantchilds of 'Start Node' (sort by name descending) with AQL query");
        ciqdNodeRepository.findByType(startType).ifPresent(startnode -> {
        final Set<CiqdNode> relations = ciqdNodeRepository.getAllChildNodesAndGrandchildNodes( "nodes/" + startnode.getId(), CiqdNodeRelationship.class);
        relations.forEach((n)->System.out.println(n.getType())); });
    }

        public Collection<Object>  createNodesAndRelationships () {
        //ObjectMapper objectMapper = new ObjectMapper();
        JSONParser parser = new JSONParser();
        List<Object> nodesAndRelationships = new ArrayList<>();
             try {
                   File file = resource.getFile();
                   JSONObject obj = (JSONObject) parser.parse(new FileReader(file));
                   JSONArray jsonArray = (JSONArray) obj.get("cells");

                   for (Object o : jsonArray) {
                    JSONObject nodeItem = (JSONObject) o;
                    String nodeType = nodeItem.get("type").toString();
                    if (nodeType != null && !nodeType.equals(edgeType)) {
                        CiqdNode ciqdNode = new CiqdNode(nodeItem.get("type").toString(), nodeItem.get("id").toString(), nodeItem.get("position").toString(), nodeItem.get("size").toString(), nodeItem.get("attrs").toString());
                        //CiqdNode ciqdNode = objectMapper.readValue(nodeItem.toString(), CiqdNode.class);
                        nodesAndRelationships.add(ciqdNode);
                    } else if (nodeType != null && nodeType.equals(edgeType)) {
                            JSONObject srcObj = (JSONObject) parser.parse(nodeItem.get("source").toString());
                            JSONObject tgtObj = (JSONObject) parser.parse(nodeItem.get("target").toString());
                            if (srcObj != null && tgtObj != null) {
                                String id = nodeItem.get("id").toString();
                                String srcId = srcObj.get("id").toString();
                                String targetId = tgtObj.get("id").toString();
                                CiqdEdge ciqdEdge = new CiqdEdge(id, srcId, targetId,edgeType);
                                nodesAndRelationships.add(ciqdEdge);
                        }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return nodesAndRelationships;
    }
}

