package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.domain.Person;
import wad.domain.TwitterFollow;
import wad.repository.PersonRepository;
import wad.repository.TwitterFollowRepository;
import wad.service.PersonService;
import wad.service.TwitterService;

@Controller
@RequestMapping("/addtwitter")
public class TwitterController {
    @Autowired
    private PersonService personService;
    
    @Autowired
    private PersonRepository people;
    
    @Autowired
    private TwitterFollowRepository follows;
    
    @Autowired
    private TwitterService twitter;
    
    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestParam String user, RedirectAttributes model) {
        boolean twitterAccountExists;
        
        try {
            twitterAccountExists = twitter.accountExists(user);
        } catch(Exception e) {
            model.addFlashAttribute("twitterAccountError", "Sorry, that account does not exist.");
            return "redirect:/index";
        }
        
        if(twitterAccountExists) {
            Person person = personService.getAuthenticatedPerson();
            TwitterFollow follow = new TwitterFollow();
            follow.setFollower(person);
            follow.setTwitterAccount(user);
            follow = follows.save(follow);
            person.getFollows().add(follow);
            people.save(person);
            return "redirect:/index";
        } else {
            model.addFlashAttribute("twitterAccountError", "Sorry, that account does not exist.");
            return "redirect:/index";
        }
    }
}
