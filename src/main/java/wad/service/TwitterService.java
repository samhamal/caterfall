package wad.service;

import java.util.List;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {
    final private String consumerKey = "BlrirAuj1O93gNH3QTZRRwP9x"; // The application's consumer key
    final private String consumerSecret = "msCuveM66RM7D5iT9KgfOXeFZOp0pbuYlHCkrEUSKSR5tqLkJ9"; // The application's consumer secret
    final private String accessToken = "753409375-mDePuq6kqEx6uIRRK7E6PWFOBNLgJIKLFTXbnYol"; // The access token granted after OAuth authorization
    final private String accessTokenSecret = "lmfV7Rx2HkimRGiQb8HQp6DgyF4gBfZwV6sh996kRizjA"; // The access token secret granted after OAuth authorization
    
    private Twitter twitter;
    
    public TwitterService() {
        twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
    
    public List<Tweet> getTweetsFromUser(String user) {
        return twitter.timelineOperations().getUserTimeline(user);
    }
}
