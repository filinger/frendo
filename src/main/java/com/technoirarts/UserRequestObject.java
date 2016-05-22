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
    private Integer ageFrom;
    private Integer ageTo;
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

    public Integer getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
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
            if ((ageFrom != null) && (ageTo != null)) {
                predicates.add(cb.and(
                    cb.greaterThanOrEqualTo(root.get(User_.age), ageFrom),
                    cb.lessThanOrEqualTo(root.get(User_.age), ageTo)
                ));
            }
            if (!city.isEmpty()) predicates.add(cb.equal(root.get(User_.city), city));
            if (!extra.isEmpty()) predicates.add(cb.equal(root.get(User_.extra), extra));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
