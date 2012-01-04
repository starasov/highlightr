package com.blogpost.starasov.highlightr;

import com.blogpost.starasov.highlightr.rank.TopicRankAggregator;
import com.blogpost.starasov.highlightr.rank.UrlRankAggregator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: starasov
 * Date: 12/29/11
 * Time: 7:32 AM
 */
public class Main {
    public static void main(String[] args) throws MalformedURLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        UrlRankAggregator urlAggregator = context.getBean("urlRankAggregator", UrlRankAggregator.class);
        TopicRankAggregator topicAggregator = context.getBean("topicRankAggregator", TopicRankAggregator.class);

        System.out.println("urlAggregator.getAggregatedRank() = " + urlAggregator.getAggregatedRank(new URL("http://xkcd.com/999/")));
        System.out.println("topicAggregator.getAggregatedRank() = " + topicAggregator.getAggregatedRank("Cougars"));
    }
}
