package com.gm.neo4j.twitter;

import com.gm.neo4j.twitter.repositories.TweetMessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@EnableNeo4jRepositories(basePackageClasses = TweetMessageRepository.class)
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@Configuration
@EnableAutoConfiguration(exclude = TwitterAutoConfiguration.class)
@ComponentScan("com.gm.neo4j.twitter")
@ConfigurationProperties(prefix = "spring.social.twitter")
public class TwitterApplication {
    private String appId;
    private String appSecret;
    private String accessToken;
    private String accessTokenSecret;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TwitterApplication.class, args);
    }

    @Bean
    public TwitterTemplate twitterTemplate() {
        return new TwitterTemplate(appId, appSecret,accessToken,accessTokenSecret);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }
}


