package com.gm.neo4j.twitter.stream;

import com.gm.neo4j.twitter.services.TwitterPollingService;
import com.gm.neo4j.twitter.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import static java.util.Arrays.asList;

@Service
@ConditionalOnProperty(name = "gm.twitter.live.stream", havingValue = "true")
public class TwitterStream implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TwitterService service;
    @Autowired
    private Twitter twitter;


    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        twitter.streamingOperations().sample(asList(new AbstractStreamListener() {
            @Override
            public void onTweet(Tweet tweet) {
                service.importTweet(tweet);
            }
        }));
    }
}
