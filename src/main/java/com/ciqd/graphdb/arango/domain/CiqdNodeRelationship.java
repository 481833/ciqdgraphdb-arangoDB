package com.ciqd.graphdb.arango.domain;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import org.springframework.data.annotation.Id;

@Edge("noderelationships")
public class CiqdNodeRelationship {

    @Id
    private String id;


    @From
    private CiqdNode srcNode;

    @To
    private CiqdNode targetNode;
    private String type;

    public CiqdNodeRelationship(final CiqdNode targetNode,final CiqdNode srcNode, final String type) {
        super();
        this.targetNode = targetNode;
        this.srcNode=srcNode;
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CiqdNode getSrcNode() {
        return srcNode;
    }

    public void setSrcNode(CiqdNode srcNode) {
        this.srcNode = srcNode;
    }

    public CiqdNode getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(CiqdNode targetNode) {
        this.targetNode = targetNode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CiqdNodeRelationship [id=" + id + ", targetNode=" + targetNode + ", srcNode=" + srcNode + "]";
    }
}
