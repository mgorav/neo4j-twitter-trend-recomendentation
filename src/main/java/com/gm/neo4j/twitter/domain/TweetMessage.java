package com.gm.neo4j.twitter.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class TweetMessage {
    @GraphId
    Long id;

    @Index(unique = true)
    Long tweetId;

    String text;

    @Relationship(type = "POSTED", direction = INCOMING)
    TweetUser poster;
    @Relationship(type = "TAGGED")
    Collection<TweetTag> tags = new HashSet<TweetTag>();
    @Relationship(type = "MENTIONS")
    Set<TweetUser> mentions = new HashSet<TweetUser>();
    @Relationship(type = "SOURCE")
    TweetMessage source;

    public TweetMessage() {
    }

    public TweetMessage(long tweetId, TweetUser poster, String text) {
        this.tweetId = tweetId;
        this.poster = poster;
        this.text = text;
    }

    public void addMention(TweetUser mention) {
        this.mentions.add(mention);
    }

    public Long getId() {
        return id;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public TweetUser getPoster() {
        return poster;
    }

    @Override
    public String toString() {
        return "Tweet " + tweetId + ": " + text + " by " + poster;
    }

    public Set<TweetUser> getMentions() {
        return mentions;
    }

    public Collection<TweetTag> getTags() {
        return tags;
    }

    public void addTag(TweetTag tag) {
        tags.add(tag);
    }

    public TweetMessage getSource() {
        return source;
    }

    public void setSource(TweetMessage source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }
}
