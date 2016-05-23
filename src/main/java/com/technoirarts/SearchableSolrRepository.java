package com.technoirarts;

import org.springframework.data.solr.core.query.Criteria;

public interface SearchableSolrRepository<T> {

    Iterable<T> search(Criteria criteria);
}
