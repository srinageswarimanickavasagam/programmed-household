package srinageswari.programmedhousehold.coreservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface RecipeRepository
    extends JpaRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {
  @Query(value = "SELECT * FROM recipe r where r.category_id = ?1", nativeQuery = true)
  List<RecipeEntity> findrecipesByCategoryId(Long categoryId);
}
