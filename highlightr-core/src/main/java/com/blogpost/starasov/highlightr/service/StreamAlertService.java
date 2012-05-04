package com.blogpost.starasov.highlightr.service;

import com.blogpost.starasov.highlightr.model.Statistics;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamAlert;
import com.blogpost.starasov.highlightr.repository.StreamAlertRepository;
import com.blogpost.starasov.highlightr.repository.StreamRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author starasov
 */
@Service
@Transactional(readOnly = true)
public class StreamAlertService {

    private static final Logger logger = LoggerFactory.getLogger(StreamAlertService.class);

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private StreamAlertRepository streamAlertRepository;

    public Page<StreamAlert> getRecentAlerts(int page, int size) {
        final DateTime lastDate = new DateTime().minusDays(1);

        return streamAlertRepository.findAll(new Specification<StreamAlert>() {
            public Predicate toPredicate(Root<StreamAlert> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("timestamp").as(Date.class), lastDate.toDate());
            }
        }, new PageRequest(page, size));
    }

    @Transactional(readOnly = false)
    @Scheduled(cron = "0 0 * * * *")
    public void runStreamCheck() {
        logger.debug("[StreamAlertService][runStreamCheck] - start");

        List<Stream> streams = findPoorStatisticStreams(2.0);
        logger.debug("[StreamAlertService][runStreamCheck] total alert streams=%s", streams.size());

        for (Stream stream : streams) {
            streamAlertRepository.save(new StreamAlert(stream));
        }

        logger.debug("[StreamAlertService][runStreamCheck] - done");
    }

    private List<Stream> findPoorStatisticStreams(double averageThreshold) {
        List<Stream> statisticsList = new ArrayList<Stream>();

        for (Stream stream : streamRepository.findAll()) {
            Statistics statistics = stream.getStatistics();
            if (statistics.getAvg() < averageThreshold) {
                statisticsList.add(stream);
            }
        }

        return statisticsList;
    }
}
