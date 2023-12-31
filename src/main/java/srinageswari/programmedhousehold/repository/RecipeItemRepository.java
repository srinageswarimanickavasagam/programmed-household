package srinageswari.programmedhousehold.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.RecipeItemEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface RecipeItemRepository extends JpaRepository<RecipeItemEntity, Long> {

  Optional<RecipeItemEntity> findByRecipeIdAndItemId(Long recipeId, Long itemId);

  boolean existsByRecipeIdAndItemId(Long recipeId, Long itemId);
}
