package com.technoirarts;

import java.io.Serializable;

public class UserRequestObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String surname;

    private String name;

    private Integer age;

    private String city;

    private String extra;

    public UserRequestObject() {}

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
