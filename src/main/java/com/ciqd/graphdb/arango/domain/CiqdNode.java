package com.ciqd.graphdb.arango.domain;


import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;
import com.arangodb.springframework.annotation.Relations;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.util.Collection;

@Document("ciqdnodes")
@HashIndex(fields={"nodeName","nodeType"}, unique = true)
public class CiqdNode {
    @Id
    private String id;
    private String nodeName;
    private String nodeType;


    @Relations(edges= CiqdNodeRelationship.class, lazy = true)
    private Collection<CiqdNode> relationNodes;

    public CiqdNode(String nodeName,String nodeType) {
        super();
        this.nodeName = nodeName;
        this.nodeType = nodeType;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Collection<CiqdNode> getRelationNodes() {
        return relationNodes;
    }
    public void setCiqdNodes(final Collection<CiqdNode> relationNodes) {
        this.relationNodes = relationNodes;
    }
}
