package com.gm.neo4j.twitter;

import com.gm.neo4j.twitter.domain.TweetMessage;
import com.gm.neo4j.twitter.services.TwitterPoolingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tweets")
public class TwitterController {

    private final TwitterPoolingService twitterPoolingService;

    public TwitterController(TwitterPoolingService twitterPoolingService) {
        this.twitterPoolingService = twitterPoolingService;
    }

    @GetMapping("/trend")
    List<TweetMessage> search(@RequestParam("search") String search) {

        return twitterPoolingService.searchTweets(search);
    }
}
