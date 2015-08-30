package wad.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private Twitter twitter;
    
    public TwitterService() {
        consumerKey = System.getenv("TWITTER_CONSUMER_KEY");
        consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET");
        accessToken = System.getenv("TWITTER_ACCESS_TOKEN");
        accessTokenSecret = System.getenv("TWITTER_ACCESS_TOKEN_SECRET");
        
        twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
    
    public List<Tweet> getTweetsFromUser(String user) {
        return twitter.timelineOperations().getUserTimeline(user);
    }
}
