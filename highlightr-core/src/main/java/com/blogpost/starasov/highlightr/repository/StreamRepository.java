package com.blogpost.starasov.highlightr.repository;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: starasov
 * Date: 1/11/12
 * Time: 7:33 AM
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface StreamRepository extends PagingAndSortingRepository<Stream, Long>, JpaSpecificationExecutor<Rank> {
    Stream findByIdentifierAndType(String identifier, StreamType type);
}
