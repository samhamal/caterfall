package wad.controller;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.AvatarImage;
import wad.domain.Person;
import wad.repository.ImageRepository;
import wad.repository.PersonRepository;
import wad.service.PersonService;

// Oma feature: Käyttäjät voivat asettaa itselleen kuvan
@Controller
@Transactional
@RequestMapping("/avatar")
public class AvatarController {
    @Autowired
    private ImageRepository images;
    
    @Autowired
    private PersonService people;
    
    @Autowired
    private PersonRepository users;
    
    @Autowired
    private HttpServletRequest request;
    
    @RequestMapping(method = RequestMethod.POST)
    public String set(@RequestParam("image") MultipartFile file) throws IOException {
        String[] OKTypes = {"image/jpeg", "image/gif", "image/png"};
        
        if(Arrays.asList(OKTypes).contains(file.getContentType())) {
            Person user = people.getAuthenticatedPerson();
            if(user.hasAvatar()) {
                images.delete(user.getAvatarId());
            }
            
            AvatarImage image = new AvatarImage();
            image.setContent(file.getBytes());
            image.setContentLength(file.getSize());
            image.setContentType(file.getContentType());
            image = images.save(image);
            user.uploadedAvatar(image.getId());
            users.save(user);
        } else {
            throw new IOException();
        }
        return "redirect:/index";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> get(@PathVariable Long id) {
        if(request.getHeader("If-None-Match") != null) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_MODIFIED);
        }
        
        Person person = users.findOne(id);
        if(person.hasAvatar()) {
            AvatarImage image = images.findOne(person.getAvatarId());
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(image.getContentType()));
            headers.setContentLength(image.getContentLength());
            headers.setCacheControl("public");
            headers.setExpires(Long.MAX_VALUE);
            headers.add("ETag", "\"" + image.getId() + "\"");

            return new ResponseEntity<>(image.getContent(), headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
}
