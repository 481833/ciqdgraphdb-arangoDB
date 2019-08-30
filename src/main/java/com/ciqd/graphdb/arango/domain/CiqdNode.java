package com.ciqd.graphdb.arango.domain;


import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;
import com.arangodb.springframework.annotation.Relations;
import org.springframework.data.annotation.Id;

import java.util.Collection;


@Document("nodes")
//@HashIndex(fields={"type"}, unique = false)
public class CiqdNode {

    @Id
    private String id;

    private String type;
    private String oldId;
    private String position;
    private String size;
    private String attrs;

    @Relations(edges=CiqdNodeRelationship.class, lazy = true)
    private Collection<CiqdNode> relationNodes;

    public CiqdNode() {
        super();
    }



    public CiqdNode(String type, String oldId, String position, String size, String attrs) {
        super();
        this.type = type;
        this.oldId = oldId;
        this.position = position;
        this.size = size;

        this.attrs = attrs;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRelationNodes(Collection<CiqdNode> relationNodes) {
        this.relationNodes = relationNodes;
    }

    public Collection<CiqdNode> getRelationNodes() {
        return relationNodes;
    }
    public void setCiqdNodes(final Collection<CiqdNode> relationNodes) {
        this.relationNodes = relationNodes;
    }

}
