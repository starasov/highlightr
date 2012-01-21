package com.blogpost.starasov.highlightr.repository;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: starasov
 * Date: 1/11/12
 * Time: 7:34 AM
 */
public interface RankRepository extends CrudRepository<Rank, Long> {
    List<Rank> findByIdentifier(String identifier);

    @Query("select r from Rank r where r.identifier = :identifier and r.stream.identifier = :streamIdentifier and r.stream.type = :streamType")
    Rank findByIdentifierAndStream(@Param("identifier") String identifier,
                                   @Param("streamIdentifier") String streamIdentifier,
                                   @Param("streamType") StreamType streamType);
}
