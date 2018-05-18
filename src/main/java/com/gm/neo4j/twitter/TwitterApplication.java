
package com.gm.neo4j.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@EnableNeo4jRepositories(basePackages = "org.neo4j.twitter_graph.repositories")
@EnableTransactionManagement
@Import(RepositoryRestMvcConfiguration.class)
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.neo4j.twitter_graph.services"})
public class TwitterApplication {

    public static final String URL = "http://localhost:7474/db/data/";


    @Bean
    public TwitterTemplate twitterTemplate() {
        return new TwitterTemplate(System.getenv("TWITTER_BEARER"));
    }


    public static void main(String[] args) throws IOException {
        SpringApplication.run(TwitterApplication.class, args);
    }
}
