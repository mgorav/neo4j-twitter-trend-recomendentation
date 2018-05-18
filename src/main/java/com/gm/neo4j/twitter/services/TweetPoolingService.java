package com.gm.neo4j.twitter.services;

import com.gm.neo4j.twitter.domain.Tag;
import com.gm.neo4j.twitter.domain.Tweet;
import com.gm.neo4j.twitter.domain.User;
import com.gm.neo4j.twitter.repositories.TagRepository;
import com.gm.neo4j.twitter.repositories.TweetRepository;
import com.gm.neo4j.twitter.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.EMPTY_MAP;

@Service
@Transactional
public class TweetPoolingService {
    private final static Log log = LogFactory.getLog(TweetPoolingService.class);

    public static String SEARCH = "#neo4j OR \"graph OR database\" OR \"graph OR databases\" OR graphdb OR graphconnect OR @neoquestions OR @Neo4jDE OR @Neo4jFr OR neotechnology OR springsource OR @SpringData OR pivotal OR @starbuxman OR @mesirii OR @springcentral";
    @Autowired
    UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    Session session;

    @Autowired
    TwitterTemplate twitterTemplate;

    public List<Tweet> importTweets(String search) {
        return importTweets(search, null);
    }

    @Scheduled(initialDelay = 10 * 1000, fixedRate = 60 * 1000)
    public void importTweets() {
        String search = System.getProperty("twitter.search", SEARCH);
        if (log.isInfoEnabled()) log.info("Importing Tweets for " + search);
        importTweets(search);
    }


    public List<Tweet> importTweets(String search, Long lastTweetId) {
        if (log.isInfoEnabled()) log.info("Importing for " + search + ", max tweet id: " + lastTweetId);

        final SearchOperations searchOperations = twitterTemplate.searchOperations();

        final SearchResults results = lastTweetId == null ? searchOperations.search(search, 200) : searchOperations.search(search, 200, lastTweetId, Long.MAX_VALUE);

        final List<Tweet> result = new ArrayList<Tweet>();
        for (org.springframework.social.twitter.api.Tweet tweet : results.getTweets()) {
            result.add(importTweet(tweet));
        }
        return result;
    }

    protected Tweet importTweet(org.springframework.social.twitter.api.Tweet source) {
        User user = userRepository.save(new User(source.getUser()));
        final String text = source.getText();
        final Tweet tweet = new Tweet(source.getId(), user, text);
        if (log.isInfoEnabled()) log.info("Imported " + tweet);
        addMentions(tweet, source.getEntities().getMentions());
        addTags(tweet, source.getEntities().getHashTags());
        return tweetRepository.save(tweet);
    }


    private void addMentions(Tweet tweet, List<MentionEntity> mentions) {
        for (MentionEntity mention : mentions) {
            tweet.addMention(userRepository.save(new User(mention.getId(), mention.getName(), mention.getScreenName())));
        }
    }

    private void addTags(Tweet tweet, List<HashTagEntity> tags) {
        for (HashTagEntity tag : tags) {
            tweet.addTag(tagRepository.save(new Tag(tag.getText())));
        }
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        session.query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r", EMPTY_MAP);
    }
}
