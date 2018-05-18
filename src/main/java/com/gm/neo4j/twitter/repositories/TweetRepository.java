package com.gm.neo4j.twitter.repositories;

import com.gm.neo4j.twitter.domain.Tweet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "tweets", path = "tweets")
public interface TweetRepository extends PagingAndSortingRepository<Tweet, Long> {
    Tweet findByTweetId(Long id);

    Collection<Tweet> findByTagsTag(String tag);

    Collection<Tweet> twitterTrend(String query);
}
