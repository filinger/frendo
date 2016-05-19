package com.technoirarts;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserRequestObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String surname;
    private String name;
    private Integer age;
    private String city;
    private String extra;

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

    public Specification<User> buildSpecification() {
        List<Predicate> predicates = new ArrayList<>();
        // TODO: find better way to create Specification based on this RequestObject.
        return (root, query, cb) -> {
            if (!surname.isEmpty()) predicates.add(cb.equal(root.get(User_.surname), surname));
            if (!name.isEmpty()) predicates.add(cb.equal(root.get(User_.name), name));
            if (age != null) predicates.add(cb.equal(root.get(User_.age), age));
            if (!city.isEmpty()) predicates.add(cb.equal(root.get(User_.city), city));
            if (!extra.isEmpty()) predicates.add(cb.equal(root.get(User_.extra), extra));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
