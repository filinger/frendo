package com.technoirarts;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 1000)
    private String extra;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> friendIds;

    public User() {
    }

    public User(Long id, String surname, String name, Integer age, String city, String extra, List<Long> friendIds) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.age = age;
        this.city = city;
        this.extra = extra;
        this.friendIds = friendIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<Long> friendIds) {
        this.friendIds = friendIds;
    }
}
