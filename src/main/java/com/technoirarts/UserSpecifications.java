package com.technoirarts;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

final public class UserSpecifications {

    private UserSpecifications() {}

    static Specification<User> findUsers(String surname, String name, Integer age, String city, String extra) {

        List<Predicate> predicates = new ArrayList<>();
        // TODO: find better way to do vreate dynamic Predicate.
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
