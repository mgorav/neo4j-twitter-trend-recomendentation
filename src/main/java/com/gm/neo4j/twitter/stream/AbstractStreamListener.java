package com.gm.neo4j.twitter.stream;

import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

public abstract class AbstractStreamListener implements StreamListener {
    /**
     * Called when a new Tweet is available on the stream
     *
     * @param tweet a tweet available on the stream
     */
    public abstract void onTweet(Tweet tweet);

    /**
     * Called when a delete message is available on the stream
     *
     * @param deleteEvent a delete event
     */
    @Override
    public void onDelete(StreamDeleteEvent deleteEvent) {

    }

    /**
     * Called when the stream is being track limited.
     *
     * @param numberOfLimitedTweets the number of tweets being limited on the stream
     */
    @Override
    public void onLimit(int numberOfLimitedTweets) {

    }

    /**
     * Called when a client is stalling and the stream is in danger of being disconnected.
     *
     * @param warningEvent a warning event
     */
    @Override
    public void onWarning(StreamWarningEvent warningEvent) {

    }
}
