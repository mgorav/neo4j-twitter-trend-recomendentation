package com.gm.neo4j.twitter.services;

import com.gm.neo4j.twitter.domain.TweetMessage;
import com.gm.neo4j.twitter.domain.TweetTag;
import com.gm.neo4j.twitter.domain.TweetUser;
import com.gm.neo4j.twitter.repositories.TweetMessageRepository;
import com.gm.neo4j.twitter.repositories.TweetTagRepository;
import com.gm.neo4j.twitter.repositories.TweetUserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Collections.EMPTY_MAP;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class TwitterPoolingService {
    private final static Log log = LogFactory.getLog(TwitterPoolingService.class);

    public String SEARCH = "#neo4j OR \"graph OR database\" OR \"graph OR databases\" OR graphdb OR graphconnect OR @neoquestions OR @Neo4jDE OR @Neo4jFr OR neotechnology OR springsource OR @SpringData OR pivotal OR @starbuxman OR @mesirii OR @springcentral";
    @Autowired
    TweetUserRepository tweetUserRepository;
    @Autowired
    TweetTagRepository tweetTagRepository;
    @Autowired
    TweetMessageRepository tweetMessageRepository;

    @Autowired
    Session session;

    @Autowired
    TwitterTemplate twitterTemplate;

    public List<TweetMessage> searchTweets(String search) {
        // NOT elegant
        synchronized (this) {
            SEARCH = search;
        }
        return searchTweets(search, null);
    }

    @Scheduled(initialDelay = 10 * 1000, fixedRate = 30 * 1000)
    public void importTweets() {
        String search = System.getProperty("gm.twitter.search", SEARCH);
        if (log.isInfoEnabled()) log.info("Importing Tweets for " + search);
        searchTweets(search);
    }


    public List<TweetMessage> searchTweets(String search, Long lastTweetId) {
        if (log.isInfoEnabled()) log.info("Importing for " + search + ", max tweet id: " + lastTweetId);

        final SearchOperations searchOperations = twitterTemplate.searchOperations();

        final SearchResults results = lastTweetId == null ? searchOperations.search(search, 100) : searchOperations.search(search, 200, lastTweetId, Long.MAX_VALUE);

        return results.getTweets().stream().map(this::importTweet).collect(toList());

    }

    protected TweetMessage importTweet(Tweet source) {
        TweetUser tweetUser = tweetUserRepository.save(new TweetUser(source.getUser()));
        final String text = source.getText();
        final TweetMessage tweetMessage = new TweetMessage(source.getId(), tweetUser, text);
        if (log.isInfoEnabled()) log.info("Imported " + tweetMessage);
        addMentions(tweetMessage, source.getEntities().getMentions());
        addTags(tweetMessage, source.getEntities().getHashTags());
        return tweetMessageRepository.save(tweetMessage);
    }


    private void addMentions(TweetMessage tweetMessage, List<MentionEntity> mentions) {
        for (MentionEntity mention : mentions) {
            tweetMessage.addMention(tweetUserRepository.save(new TweetUser(mention.getId(), mention.getName(), mention.getScreenName())));
        }
    }

    private void addTags(TweetMessage tweetMessage, List<HashTagEntity> tags) {
        for (HashTagEntity tag : tags) {
            tweetMessage.addTag(tweetTagRepository.save(new TweetTag(tag.getText())));
        }
    }

    @PostConstruct
    public void postConstruct() {
        session.query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r", EMPTY_MAP);
    }
}
