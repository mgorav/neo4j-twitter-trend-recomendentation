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
import static java.util.stream.StreamSupport.stream;

@Service
@Transactional
public class TwitterPollingService {
    private final static Log log = LogFactory.getLog(TwitterPollingService.class);

    public String TWITTER_SEARCH = "#neo4j OR \"graph OR database\" OR \"graph OR databases\" OR graphdb OR graphconnect OR @neoquestions OR @Neo4jDE OR @Neo4jFr OR neotechnology OR springsource OR @SpringData OR pivotal OR @starbuxman OR @mesirii OR @springcentral";
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


    public List<TweetMessage> searchAndImportTweetsInTwitter(String search) {
        // This method also search tweets using pagination and store in neo4j
        return getSearchResults(search, null).getTweets().stream().map(this::importTweet).collect(toList());
    }

    public List<TweetMessage> searchByCypher(String cypher) {

        return (List<TweetMessage>) stream(session.query(TweetMessage.class, cypher, EMPTY_MAP).spliterator(), false).collect(toList());
    }

    @Scheduled(initialDelay = 10 * 1000, fixedRate = 30 * 1000)
    public void scheduleImportTweets() {
        String search = System.getProperty("gm.twitter.search", TWITTER_SEARCH);
        if (log.isInfoEnabled()) {
            log.info("Importing Tweets for " + search);
        }
        searchAndImportTweetsInTwitter(search);
    }


    public List<TweetMessage> searchAndImportTweetsInTwitter(String search, Long lastTweetId) {
        if (log.isInfoEnabled()) {
            log.info("Importing for " + search + ", max tweet id: " + lastTweetId);
        }

        final SearchResults results = getSearchResults(search, lastTweetId);

        return results.getTweets().stream().map(this::importTweet).collect(toList());

    }

    private SearchResults getSearchResults(String search, Long lastTweetId) {
        final SearchOperations searchOperations = twitterTemplate.searchOperations();

        return lastTweetId == null ? searchOperations.search(search, 100) : searchOperations.search(search, 200, lastTweetId, Long.MAX_VALUE);
    }

    protected TweetMessage importTweet(Tweet source) {
        TweetUser tweetUser = tweetUserRepository.save(new TweetUser(source.getUser()));
        final String text = source.getText();
        final TweetMessage tweetMessage = new TweetMessage(source.getId(), tweetUser, text);
        if (log.isInfoEnabled()) {
            log.info("Imported " + tweetMessage);
        }
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
