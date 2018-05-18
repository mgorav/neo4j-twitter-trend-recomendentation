package com.gm.neo4j.twitter.repositories;

import com.gm.neo4j.twitter.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("MATCH (me:User {user:{name}})-[:POSTED]->(tweet)-[:MENTIONS]->(user)" +
            " WHERE me <> user " +
            " RETURN distinct user")
    Set<User> suggestFriends(@Param("name") String user);

    User findByUser(@Param("0") String user);

}
