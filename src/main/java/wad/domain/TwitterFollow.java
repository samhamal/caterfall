package wad.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class TwitterFollow extends AbstractResource {
    @NotNull
    @NotEmpty
    private String twitterAccount;
    
    @ManyToOne
    private Person follower;

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public Person getFollower() {
        return follower;
    }

    public void setFollower(Person follower) {
        this.follower = follower;
    }
}
