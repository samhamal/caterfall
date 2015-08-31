package wad.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.social.twitter.api.Tweet;
import wad.domain.Entry;
import wad.domain.Person;
import wad.domain.Post;
import wad.domain.TwitterFollow;

public class EntryBuilder {
    List<Entry> entries;

    public EntryBuilder() {
        entries = new ArrayList<>();
    }
    
    public void getPostsAndFollowedTweets(Person user) {
        List<TwitterFollow> follows = user.getFollows();
        
        // todo: cache
        for(TwitterFollow follow : follows) {
            
        }
    }
    
    public void addTweets(List<Tweet> tweets) {
        for(Tweet tweet : tweets) entries.add(new Entry(tweet));
    }
    
    public void addPosts(List<Post> posts) {
        for(Post post : posts) entries.add(new Entry(post));
    }
    
    public List<Entry> getEntries() {
        return entries;
    }
    
    public void sort() {
        Collections.sort(entries);
    }
}
