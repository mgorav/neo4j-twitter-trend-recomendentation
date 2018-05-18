package com.gm.neo4j.twitter.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class TweetTag {
    @GraphId
    Long id;
    @Index(unique = true)
    private String tag;

    public TweetTag() {
    }

    public TweetTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "#" + tag;
    }
}
