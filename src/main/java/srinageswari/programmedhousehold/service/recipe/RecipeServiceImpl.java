package srinageswari.programmedhousehold.service.recipe;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.common.Constants;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.common.search.SearchSpecification;
import srinageswari.programmedhousehold.dto.recipe.RecipeMapper;
import srinageswari.programmedhousehold.dto.recipe.RecipeRequestDTO;
import srinageswari.programmedhousehold.dto.recipe.RecipeResponseDTO;
import srinageswari.programmedhousehold.dto.recipeingredient.RecipeIngredientResponseDTO;
import srinageswari.programmedhousehold.model.Item;
import srinageswari.programmedhousehold.model.Recipe;
import srinageswari.programmedhousehold.model.RecipeIngredient;
import srinageswari.programmedhousehold.model.Schedule;
import srinageswari.programmedhousehold.repository.ItemRepository;
import srinageswari.programmedhousehold.repository.RecipeRepository;
import srinageswari.programmedhousehold.repository.ScheduleRepository;
import srinageswari.programmedhousehold.service.appuser.AppUserServiceImpl;
import srinageswari.programmedhousehold.service.category.ICategoryService;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, updating, removing and fetching recipes
 */
@Slf4j(topic = "RecipeServiceImpl")
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {
  private final RecipeRepository recipeRepository;
  private final ItemRepository itemRepository;
  private final RecipeMapper recipeMapper;
  private final AppUserServiceImpl appUserServiceImpl;
  private final ICategoryService categoryService;
  private final ScheduleRepository scheduleRepository;

  /**
   * Fetches a recipe by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public RecipeResponseDTO findById(Long id) {
    return recipeRepository
        .findById(id)
        .map(
            recipe ->
                new RecipeResponseDTO(
                    recipe,
                    recipe.getRecipeIngredients().stream()
                        .map(RecipeIngredientResponseDTO::new)
                        .toList()))
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_RECIPE);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
            });
  }

  /**
   * Fetches all recipes based on the given recipe filter parameters
   *
   * @param request
   * @return Paginated recipe data
   */
  @Transactional(readOnly = true)
  public Page<RecipeResponseDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<Recipe> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<RecipeResponseDTO> recipes =
        recipeRepository
            .findAll(specification, pageable)
            .map(
                recipe ->
                    new RecipeResponseDTO(
                        recipe,
                        recipe.getRecipeIngredients().stream()
                            .map(RecipeIngredientResponseDTO::new)
                            .toList()));
    if (recipes.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return recipes;
  }

  /**
   * Creates a new recipe and ingredients belonging to the recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CommandResponseDTO create(RecipeRequestDTO request) {
    final Recipe recipe = recipeMapper.toEntity(request);
    recipe.getRecipeIngredients().clear();
    request.getRecipeIngredients().stream()
        .forEach(
            recipeIngredient -> {
              final Item item;
              if (recipeIngredient.getIngredientId() != 0) {
                item =
                    itemRepository
                        .findById(recipeIngredient.getIngredientId())
                        .orElseThrow(
                            () -> {
                              log.error(Constants.NOT_FOUND_INGREDIENT);
                              return new NoSuchElementFoundException(
                                  Constants.NOT_FOUND_INGREDIENT);
                            });
              } else {
                // check if the new ingredient is already defined before
                if (itemRepository.existsByNameIgnoreCase(recipeIngredient.getIngredientName())) {
                  log.error(
                      String.format(
                          Constants.ALREADY_EXISTS_INGREDIENT, recipeIngredient.getIngredientId()));
                  throw new ElementAlreadyExistsException(
                      String.format(
                          Constants.ALREADY_EXISTS_INGREDIENT, recipeIngredient.getIngredientId()));
                }
                item = itemRepository.save(new Item(0L, recipeIngredient.getIngredientName()));
              }
              recipe.addRecipeIngredient(
                  new RecipeIngredient(
                      recipe,
                      item,
                      recipeIngredient.getUnit(),
                      recipeIngredient.getIngredientQty()));
            });
    recipe.setAppUser(appUserServiceImpl.getCurrentLoggedInUser());
    recipeRepository.save(recipe);
    if (recipe.isActive()) {
      Schedule schedule = new Schedule(recipe);
      scheduleRepository.save(schedule);
    }
    return CommandResponseDTO.builder().id(recipe.getId()).build();
  }

  /**
   * Updates recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing ingredients for a current recipe, use RecipeIngredientService methods
  public CommandResponseDTO update(RecipeRequestDTO request) {
    final Recipe recipe =
        recipeRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipe.setTitle(capitalizeFully(request.getTitle()));
    recipe.setDescription(request.getDescription());
    recipe.setPrepTime(request.getPrepTime());
    recipe.setCookTime(request.getCookTime());
    recipe.setServings(request.getServings());
    recipe.setInstructions(request.getInstructions());
    recipe.setHealthLabel(request.getHealthLabel());
    recipeRepository.save(recipe);
    return CommandResponseDTO.builder().id(recipe.getId()).build();
  }

  /**
   * Deletes recipe by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final Recipe recipe =
        recipeRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeRepository.delete(recipe);
  }

  public List<RecipeResponseDTO> getRecipeByCategoryId(Long id) {
    return recipeRepository.findrecipesByCategoryId(id).stream()
        .map(
            recipe ->
                new RecipeResponseDTO(
                    recipe,
                    recipe.getRecipeIngredients().stream()
                        .map(RecipeIngredientResponseDTO::new)
                        .toList()))
        .toList();
  }
}
