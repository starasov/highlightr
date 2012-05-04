package com.blogpost.starasov.highlightr.repository;

import com.blogpost.starasov.highlightr.model.StreamAlert;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
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
public interface StreamAlertRepository extends CrudRepository<StreamAlert, Long>, JpaSpecificationExecutor<StreamAlert> {
}
