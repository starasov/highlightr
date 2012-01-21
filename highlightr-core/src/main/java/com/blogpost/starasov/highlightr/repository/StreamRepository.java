package com.blogpost.starasov.highlightr.repository;

import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import org.springframework.data.repository.CrudRepository;

/**
 * User: starasov
 * Date: 1/11/12
 * Time: 7:33 AM
 */
public interface StreamRepository extends CrudRepository<Stream, Long> {
    Stream findByIdentifierAndType(String identifier, StreamType type);
}
