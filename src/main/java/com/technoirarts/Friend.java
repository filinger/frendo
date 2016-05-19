package com.technoirarts;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Friend implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne(fetch= FetchType.LAZY)
    private User user;

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
