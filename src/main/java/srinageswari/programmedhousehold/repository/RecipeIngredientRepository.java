package srinageswari.programmedhousehold.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.RecipeIngredient;

/**
 * @author smanickavasagam
 */
@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

  Optional<RecipeIngredient> findByRecipeIdAndItemId(Long recipeId, Long itemId);

  boolean existsByRecipeIdAndItemId(Long recipeId, Long itemId);
}
