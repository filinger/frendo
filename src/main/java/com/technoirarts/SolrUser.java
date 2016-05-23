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

    @Field("surname_s")
    private String surname;

    @Field("name_s")
    private String name;

    @Field("age_i")
    private Integer age;

    @Field("city_s")
    private String city;

    @Field("extra_en")
    private String extra;

    static public SolrUser fromUser(User user) {
        SolrUser solrUser = new SolrUser();
        solrUser.setUserId(user.getId());
        solrUser.setSurname(user.getSurname());
        solrUser.setName(user.getName());
        solrUser.setAge(user.getAge());
        solrUser.setCity(user.getCity());
        solrUser.setExtra(user.getExtra());
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
