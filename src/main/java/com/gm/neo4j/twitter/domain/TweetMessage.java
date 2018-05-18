package com.gm.neo4j.twitter.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;
import java.util.Date;
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

    Date createdAt;

    String languageCode;

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

    public TweetMessage(long tweetId, TweetUser poster, String text,Date createdAt,String languageCode) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getLanguageCode() {
        return languageCode;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TweetMessage{");
        sb.append("id=").append(id);
        sb.append(", tweetId=").append(tweetId);
        sb.append(", text='").append(text).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", languageCode='").append(languageCode).append('\'');
        sb.append(", poster=").append(poster);
        sb.append(", tags=").append(tags);
        sb.append(", mentions=").append(mentions);
        sb.append(", source=").append(source);
        sb.append('}');
        return sb.toString();
    }
}
