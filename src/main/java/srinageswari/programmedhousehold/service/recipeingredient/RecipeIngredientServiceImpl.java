package srinageswari.programmedhousehold.service.recipeingredient;

import static srinageswari.programmedhousehold.common.Constants.ALREADY_EXISTS_INGREDIENT;
import static srinageswari.programmedhousehold.common.Constants.NOT_FOUND_INGREDIENT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientRequestDTO;
import srinageswari.programmedhousehold.model.Item;
import srinageswari.programmedhousehold.model.Recipe;
import srinageswari.programmedhousehold.model.RecipeIngredient;
import srinageswari.programmedhousehold.repository.ItemRepository;
import srinageswari.programmedhousehold.repository.RecipeIngredientRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding and removing recipeIngredient
 */
@Slf4j(topic = "RecipeIngredientService")
@Service
@RequiredArgsConstructor
public class RecipeIngredientServiceImpl implements IRecipeIngredientService {

  private final RecipeIngredientRepository recipeIngredientRepository;
  private final ItemRepository itemRepository;

  /**
   * Adds ingredient to the given recipe
   *
   * @param request
   * @return
   */
  @Transactional
  public CommandResponseDTO addIngredientToRecipe(RecipeIngredientRequestDTO request) {
    final Item item;
    if (request.getIngredientId() != 0) {
      // check if the ingredient is already defined for the recipe
      if (recipeIngredientRepository.existsByRecipeIdAndItemId(
          request.getRecipeId(), request.getIngredientId())) {
        log.error(ALREADY_EXISTS_INGREDIENT);
        throw new ElementAlreadyExistsException(
            String.format(ALREADY_EXISTS_INGREDIENT, request.getIngredientId()));
      }
      item = new Item(request.getIngredientId());
    } else {
      // check if the new ingredient is already defined before
      if (itemRepository.existsByNameIgnoreCase(request.getIngredientName())) {
        log.error(ALREADY_EXISTS_INGREDIENT);
        throw new ElementAlreadyExistsException(
            String.format(ALREADY_EXISTS_INGREDIENT, request.getIngredientId()));
      }
      item = itemRepository.save(new Item(0L, request.getIngredientName()));
    }
    // if needed, we can also check if recipe and unit values exists in db (we assumed recipe is
    // already defined and unit is selected from the list)
    final Recipe recipe = new Recipe(request.getRecipeId());
    final RecipeIngredient recipeIngredient =
        new RecipeIngredient(recipe, item, request.getUnit(), request.getIngredientQty());

    recipeIngredientRepository.save(recipeIngredient);
    return CommandResponseDTO.builder().id(recipeIngredient.getRecipe().getId()).build();
  }

  /**
   * Removes ingredient from the given recipe
   *
   * @param recipeId
   * @param ingredientId
   * @return
   */
  public void removeIngredientFromRecipe(Long recipeId, Long ingredientId) {
    final RecipeIngredient recipeIngredient =
        recipeIngredientRepository
            .findByRecipeIdAndItemId(recipeId, ingredientId)
            .orElseThrow(
                () -> {
                  log.error(NOT_FOUND_INGREDIENT);
                  return new NoSuchElementFoundException(NOT_FOUND_INGREDIENT);
                });
    recipeIngredientRepository.delete(recipeIngredient);
  }
}
