package wad.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class Person extends AbstractPersistable<Long> {

    private String name;
    private String slogan;

    @Column(unique = true)
    private String username;
    private String password;
    private String salt;
    
    @OneToOne(fetch = FetchType.LAZY)
    private AvatarImage image;
    
    boolean hasAvatar;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;
    
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<TwitterFollow> follows;
    
    public boolean hasAvatar() {
        return hasAvatar;
    }
    
    public void setHasAvatar() {
        hasAvatar = true;
    }

    public Person() {
        this.lastUpdated = new Date();
        hasAvatar = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
    public AvatarImage getImage() {
        return image;
    }

    public void setImage(AvatarImage image) {
        this.image = image;
    }

    public List<TwitterFollow> getFollows() {
        return follows;
    }

    public void setFollows(List<TwitterFollow> follows) {
        this.follows = follows;
    }
}
