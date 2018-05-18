package com.gm.neo4j.twitter.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Tweet {
    @GraphId
    Long id;

    @Index(unique=true) Long tweetId;

    String text;

     @Relationship(type="POSTED", direction = Relationship.INCOMING) User poster;
     @Relationship(type="TAGGED")   Collection<Tag> tags=new HashSet<Tag>();
     @Relationship(type="MENTIONS") Set<User> mentions=new HashSet<User>();
     @Relationship(type="SOURCE")   Tweet source;

    public Tweet() {
    }

    public Tweet(long tweetId, User poster, String text) {
        this.tweetId = tweetId;
        this.poster = poster;
        this.text = text;
    }

    public void addMention(User mention) {
        this.mentions.add(mention);
    }
    public Long getId() {
        return id;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public User getPoster() {
        return poster;
    }

    @Override
    public String toString() {
        return "Tweet " + tweetId + ": " + text + " by " + poster;
    }

    public Set<User> getMentions() {
        return mentions;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void setSource(Tweet source) {
        this.source = source;
    }

    public Tweet getSource() {
        return source;
    }

    public String getText() {
        return text;
    }
}
