package com.blogpost.starasov.highlightr.repository;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.StreamType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: starasov
 * Date: 1/11/12
 * Time: 7:34 AM
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RankRepository extends CrudRepository<Rank, Long>, JpaSpecificationExecutor<Rank> {
    @Query("select r from Rank r where r.identifier = :identifier and r.stream.identifier = :streamIdentifier and r.stream.type = :streamType")
    Rank findByIdentifierAndStream(@Param("identifier") String identifier,
                                   @Param("streamIdentifier") String streamIdentifier,
                                   @Param("streamType") StreamType streamType);
}
