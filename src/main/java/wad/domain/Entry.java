package wad.domain;

import java.util.Date;
import org.springframework.social.twitter.api.Tweet;

public class Entry implements Comparable<Entry>{
    private Tweet tweet;
    private Post post;
    private boolean fromTwitter;

    public boolean isFromTwitter() {
        return fromTwitter;
    }
    
    public Post getPost() {
        return post;
    }
    
    public Tweet getTweet() {
        return tweet;
    }
    
    public Entry(Tweet tweet) {
        fromTwitter = true;
        this.tweet = tweet;
    }
    
    public Entry(Post post) {
        fromTwitter = false;
        this.post = post;
    }
    
    public Date date() {
        return fromTwitter ? tweet.getCreatedAt() : post.getDate();
    }
    
    @Override
    public int compareTo(Entry other) {
        return this.date().after(other.date()) ? -1 : 1;
    }
}
