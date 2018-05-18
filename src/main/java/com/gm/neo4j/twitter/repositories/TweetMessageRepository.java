package com.gm.neo4j.twitter.repositories;

import com.gm.neo4j.twitter.domain.TweetMessage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "tweets", path = "tweets")
public interface TweetMessageRepository extends PagingAndSortingRepository<TweetMessage, Long> {
    TweetMessage findByTweetId(Long id);

    Collection<TweetMessage> findByTagsTag(String tag);

}
