package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Person;
import wad.domain.TwitterFollow;
import wad.repository.PersonRepository;
import wad.repository.TwitterFollowRepository;
import wad.service.PersonService;

@Controller
@RequestMapping("/addtwitter")
public class TwitterController {
    @Autowired
    private PersonService personService;
    
    @Autowired
    private PersonRepository people;
    
    @Autowired
    private TwitterFollowRepository follows;
    
    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestParam String user) {
        Person person = personService.getAuthenticatedPerson();
        TwitterFollow follow = new TwitterFollow();
        follow.setFollower(person);
        follow.setTwitterAccount(user);
        follow = follows.save(follow);
        person.getFollows().add(follow);
        people.save(person);
        return "redirect:/index";
    }
}
