package com.technoirarts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.Cursor;

import java.util.Iterator;

public class SimpleSearchableSolrRepository<T> implements SearchableSolrRepository<T> {

    private SolrOperations solrOperations;
    private Class<T> documentType;

    public SimpleSearchableSolrRepository(SolrOperations solrOperations, Class<T> documentType) {
        this.solrOperations = solrOperations;
        this.documentType = documentType;
    }

    @Override
    public Iterable<T> search(Criteria criteria) {
        Query query = new SimpleQuery(criteria);
        int count = (int) solrOperations.count(query);
        Pageable pageable = new SolrPageRequest(0, count);
        Page<T> page = solrOperations.queryForPage(query.setPageRequest(pageable), documentType);
        return page.getContent();
    }

    private class CursorIterable implements Iterable<T> {

        private final Cursor<T> cursor;

        public CursorIterable(Cursor<T> cursor) {
            this.cursor = cursor;
        }

        @Override
        public Iterator<T> iterator() {
            return cursor;
        }
    }
}
