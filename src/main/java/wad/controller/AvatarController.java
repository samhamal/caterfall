package wad.controller;

import java.io.IOException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/avatar")
public class AvatarController {
    @Autowired
    private ImageRepository images;
    
    @Autowired
    private PersonService people;
    
    @Autowired
    private PersonRepository users;
    
    @RequestMapping(method = RequestMethod.POST)
    public String set(@RequestParam("image") MultipartFile file) throws IOException {
        String[] OKTypes = {"image/jpeg", "image/gif", "image/png"};
        
        if(Arrays.asList(OKTypes).contains(file.getContentType())) {
            Person user = people.getAuthenticatedPerson();
            if(user.getImage() != null) {
                images.delete(user.getImage().getId());
            }
            
            AvatarImage image = new AvatarImage();
            image.setContent(file.getBytes());
            image.setContentLength(file.getSize());
            image.setContentType(file.getContentType());
            image.setOwner(user);
            image = images.save(image);
            user.setImage(image);
            users.save(user);
        } else {
            throw new IOException();
        }
        return "redirect:/index";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> get(@PathVariable Long id) {
        AvatarImage image = images.findOne(id);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getContentType()));
        headers.setContentLength(image.getContentLength());
        
        return new ResponseEntity<>(image.getContent(), headers, HttpStatus.CREATED);
    }
}
