package com.gm.neo4j.twitter.repositories;

import com.gm.neo4j.twitter.domain.TweetTag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tags", path = "tags")
public interface TweetTagRepository extends PagingAndSortingRepository<TweetTag,Long> {
}
