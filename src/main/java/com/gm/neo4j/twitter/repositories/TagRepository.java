package com.gm.neo4j.twitter.repositories;

import com.gm.neo4j.twitter.domain.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author mh
 * @since 24.07.12
 */
@RepositoryRestResource(collectionResourceRel = "tags", path = "tags")
public interface TagRepository extends PagingAndSortingRepository<Tag,Long> {
}
