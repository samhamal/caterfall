package wad.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Entry;
import wad.domain.FriendshipRequest;
import wad.domain.FriendshipRequest.Status;
import wad.domain.Person;
import wad.domain.Post;
import wad.domain.TwitterFollow;
import wad.repository.FriendshipRequestRepository;
import wad.repository.PersonRepository;
import wad.repository.PostRepository;
import wad.service.EntryBuilder;
import wad.service.LikeService;
import wad.service.PersonService;
import wad.service.TwitterService;

@Controller
@RequestMapping("*")
public class DefaultController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private LikeService likeService;
    
    @Autowired
    private TwitterService twitter;
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String viewLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String viewSignup(Model model) {
        return "signup";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        EntryBuilder entryBuilder = new EntryBuilder();
        PageRequest pr = new PageRequest(0, 10, Sort.Direction.DESC, "date");

        Person self = personService.getAuthenticatedPerson();
        model.addAttribute("self", self);
        
        List<Tweet> tweets = new ArrayList<>();
        List<TwitterFollow> follows = self.getFollows();
        if(!follows.isEmpty()) {
            for(TwitterFollow follow : follows) {
                tweets.addAll(twitter.getTweetsFromUser(follow.getTwitterAccount()));
            }
            
            entryBuilder.addTweets(tweets);
        }

        List<FriendshipRequest> friends = friendshipRequestRepository.findBySourceOrTargetAndStatus(self, self, Status.ACCEPTED);
        Set<Person> persons = new HashSet<>();
        for (FriendshipRequest friend : friends) {
            persons.add(friend.getSource());
            persons.add(friend.getTarget());
        }

        if (!persons.isEmpty()) {
            List<Post> posts = postRepository.findByAuthorIn(persons, pr);
            entryBuilder.addPosts(posts);
        }
        
        entryBuilder.sort();
        model.addAttribute("posts", entryBuilder.getEntries());

        pr = new PageRequest(0, 10, Sort.Direction.DESC, "lastUpdated");
        List<Person> userList = new ArrayList<>(personRepository.findAll(pr).getContent());
        userList.remove(self);
        model.addAttribute("users", userList);

        List<FriendshipRequest> requests = friendshipRequestRepository.findByTargetAndStatus(self, Status.REQUESTED);
        if (requests != null && requests.size() > 0) {
            model.addAttribute("friendshipRequests", requests);
        }

        return "index";
    }
}
