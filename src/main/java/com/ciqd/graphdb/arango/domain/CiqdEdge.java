package com.ciqd.graphdb.arango.domain;

import org.springframework.data.annotation.Id;

public class CiqdEdge {

    @Id
    private String id;

    private String oldId;
    private String sourceId;
    private String targetId;
    private String type;

    public CiqdEdge() {
        super();
    }

    public CiqdEdge(String oldId, String sourceId, String targetId,String type) {
        super();
        this.oldId = oldId;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.type = type;
    }

    public String getId() {
        return oldId;
    }

    public void setId(String id) {
        this.oldId = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CiqdEdge [id=" + oldId + ", targetNode=" + sourceId + ", srcNode=" + targetId + "]";
    }
}
