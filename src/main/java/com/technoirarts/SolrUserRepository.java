package com.technoirarts;

import org.springframework.data.solr.repository.SolrCrudRepository;

public interface SolrUserRepository extends SolrCrudRepository<User, Long> {

    Iterable<User> findByExtra(String extra);
}
