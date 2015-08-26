package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Person;
import wad.domain.TwitterFollow;

public interface TwitterFollowRepository extends JpaRepository<TwitterFollow, String> {
    List<TwitterFollow> findByFollower(Person person);
}
