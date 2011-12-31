package com.blogpost.starasov.highlightr;

import com.blogpost.starasov.highlightr.rank.TopicRank;
import com.blogpost.starasov.highlightr.rank.UrlRank;
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
//        TopicRank topicRank = context.getBean("diggRank", TopicRank.class);
        UrlRank urlRank = context.getBean("twitterUrlRank", UrlRank.class);
        urlRank.get(new URL("http://ottopress.com/2010/twitters-new-tweet-button-and-the-count-api/"));
//        System.out.println(topicRank.get("Scripting Layer for Android"));
    }
}
