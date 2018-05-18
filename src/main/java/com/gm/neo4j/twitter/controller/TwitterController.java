package com.gm.neo4j.twitter.controller;

import com.gm.neo4j.twitter.domain.TweetMessage;
import com.gm.neo4j.twitter.services.TwitterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tweets")
public class TwitterController {

    private final TwitterService service;

    public TwitterController(TwitterService service) {
        this.service = service;
    }

    @PostMapping("/importweets")
    void searchAndImport(@RequestParam("search") String search, @RequestParam("tweetId") Long tweetId) {

        service.searchAndImportTweetsInTwitter(search, tweetId);
    }

    @GetMapping("/trend")
    List<TweetMessage> searchByCypher(@RequestParam("cypher") String cypher) {

        return service.searchByCypher(cypher);
    }
}
