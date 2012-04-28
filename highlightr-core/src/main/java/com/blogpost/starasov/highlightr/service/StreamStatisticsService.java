package com.blogpost.starasov.highlightr.service;

import com.blogpost.starasov.highlightr.model.Statistics;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamStatistics;
import com.blogpost.starasov.highlightr.repository.StreamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: starasov
 * Date: 1/6/12
 * Time: 7:53 AM
 */
@Service
@Transactional(readOnly = true)
public class StreamStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(StreamStatisticsService.class);

    @Autowired
    private StreamRepository streamRepository;

    public List<StreamStatistics> findPoorStatisticStreams(double averageThreshold) {

        List<StreamStatistics> statisticsList = new ArrayList<StreamStatistics>();
        for (Stream stream : streamRepository.findAll()) {
            Statistics statistics = stream.getStatistics();
            if (statistics.getAvg() < averageThreshold) {
                statisticsList.add(new StreamStatistics(stream, statistics));
            }
        }

        return statisticsList;
    }
}
