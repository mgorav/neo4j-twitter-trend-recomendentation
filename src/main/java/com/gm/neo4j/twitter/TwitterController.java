package com.gm.neo4j.twitter;

import com.gm.neo4j.twitter.domain.TweetMessage;
import com.gm.neo4j.twitter.services.TwitterPollingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tweets")
public class TwitterController {

    private final TwitterPollingService twitterPollingService;

    public TwitterController(TwitterPollingService twitterPollingService) {
        this.twitterPollingService = twitterPollingService;
    }

    @PostMapping("/importweets")
    void searchAndImport(@RequestParam("search") String search, @RequestParam("tweetId") Long tweetId) {

        twitterPollingService.searchAndImportTweetsInTwitter(search, tweetId);
    }

    @GetMapping("/trend")
    List<TweetMessage> searchByCypher(@RequestParam("cypher") String cypher) {

        return twitterPollingService.searchByCypher(cypher);
    }
}
