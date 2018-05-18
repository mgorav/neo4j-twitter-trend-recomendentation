package com.gm.neo4j.twitter.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "gm.twitter.live.stream", havingValue = "false")
public class TwitterPollingService {
    private final static Log log = LogFactory.getLog(TwitterPollingService.class);

    public String TWITTER_SEARCH = "#neo4j OR \"graph OR database\" OR \"graph OR databases\" OR graphdb OR graphconnect OR @neoquestions OR @Neo4jDE OR @Neo4jFr OR neotechnology OR springsource OR @SpringData OR pivotal OR @starbuxman OR @mesirii OR @springcentral";

    @Autowired
    TwitterService twitterService;


    @Scheduled(initialDelay = 10 * 1000, fixedRate = 30 * 1000)
    public void scheduleImportTweets() {
        String search = System.getProperty("gm.twitter.search", TWITTER_SEARCH);
        if (log.isInfoEnabled()) {
            log.info("Importing Tweets for " + search);
        }
        twitterService.searchAndImportTweetsInTwitter(search, null);
    }


}
