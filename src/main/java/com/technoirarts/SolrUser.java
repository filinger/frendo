package com.technoirarts;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection1")
public class SolrUser {

    @Id
    @Field
    private String id;

    @Field("userId_l")
    private Long userId;

    @Field("name_s")
    private String name;

    static public SolrUser fromUser(User user) {
        SolrUser solrUser = new SolrUser();
        solrUser.setUserId(user.getId());
        solrUser.setName(user.getName());
        return solrUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
