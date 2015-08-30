package wad.service;

import java.util.List;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {
    final private String consumerKey = System.getenv("TWITTER_CONSUMER_KEY");
    final private String consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET");
    final private String accessToken = System.getenv("TWITTER_ACCESS_TOKEN");
    final private String accessTokenSecret = System.getenv("TWITTER_ACCESS_TOKEN_SECRET");
    
    private Twitter twitter;
    
    public TwitterService() {
        twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
    
    public List<Tweet> getTweetsFromUser(String user) {
        return twitter.timelineOperations().getUserTimeline(user);
    }
}
