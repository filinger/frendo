package com.technoirarts;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Friend implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Friend() {
    }

    public Friend(Long id, User user) {
        Id = id;
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
