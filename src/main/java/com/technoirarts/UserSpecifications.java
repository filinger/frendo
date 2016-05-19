package com.technoirarts;

import org.springframework.data.jpa.domain.Specification;

final public class UserSpecifications {

    private UserSpecifications() {}

    static Specification<User> findUsers(Long id, String surname, String name, Integer age, String city, String extra) {
        return (root, query, cb) -> cb.or(
            cb.equal(root.get(User_.id), id),
            cb.equal(root.get(User_.surname), surname),
            cb.equal(root.get(User_.name), name),
            cb.equal(root.get(User_.age), age),
            cb.equal(root.get(User_.city), city),
            cb.equal(root.get(User_.extra), extra)
        );
    }

}
