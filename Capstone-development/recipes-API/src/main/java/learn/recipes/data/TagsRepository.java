package learn.recipes.data;


import learn.recipes.models.AppUser;
import learn.recipes.models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {

    Tags findByTagName(String tagName);

}
