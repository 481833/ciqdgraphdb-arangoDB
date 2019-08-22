package com.ciqd.graphdb.arango.domain;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

@Edge
public class CiqdNodeRelationship {
    @Id
    @GeneratedValue
    private String relationshipId;

    @From
    private CiqdNode srcNode;

    @To
    private CiqdNode targetNode;

    public CiqdNodeRelationship(CiqdNode srcNode, CiqdNode targetNode) {
        this.srcNode = srcNode;
        this.targetNode=targetNode;
    }

    public String getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
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

    @Override
    public String toString() {
        return "ChildOf [id=" + relationshipId + ", targetNode=" + targetNode + ", srcNode=" + srcNode + "]";
    }

}
