package com.gm.neo4j.twitter;

import com.gm.neo4j.twitter.domain.TweetMessage;
import com.gm.neo4j.twitter.services.TwitterPollingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tweets")
public class TwitterController {

    private final TwitterPollingService twitterPollingService;

    public TwitterController(TwitterPollingService twitterPollingService) {
        this.twitterPollingService = twitterPollingService;
    }

    @GetMapping("/importweets")
    List<TweetMessage> searchAndImport(@RequestParam("search") String search,@RequestParam("tweetId") Long tweetId ) {

        return tweetId == null ? twitterPollingService.searchAndImportTweetsInTwitter(search) :
                twitterPollingService.searchAndImportTweetsInTwitter(search,tweetId) ;
    }

    @GetMapping("/trend")
    List<TweetMessage> searchByCypher(@RequestParam("cypher") String cypher) {

        return twitterPollingService.searchByCypher(cypher);
    }
}
