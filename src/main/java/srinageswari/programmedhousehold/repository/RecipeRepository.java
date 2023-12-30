package srinageswari.programmedhousehold.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.Recipe;

/**
 * @author smanickavasagam
 */
@Repository
public interface RecipeRepository
    extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
  @Query(value = "SELECT * FROM recipe r where r.category_id = ?1", nativeQuery = true)
  List<Recipe> findrecipesByCategoryId(Long categoryId);
}
