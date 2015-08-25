package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.AvatarImage;


public interface ImageRepository extends JpaRepository<AvatarImage, Long> {

}
