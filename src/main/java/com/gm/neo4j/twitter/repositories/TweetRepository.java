package com.gm.neo4j.twitter.repositories;

import com.gm.neo4j.twitter.domain.Tweet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * @author mh
 * @since 24.07.12
 */
@RepositoryRestResource(collectionResourceRel = "tweets", path = "tweets")
public interface TweetRepository extends PagingAndSortingRepository<Tweet, Long> {
    Tweet findByTweetId(Long id);

    Collection<Tweet> findByTagsTag(String tag);
}
