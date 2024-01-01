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
import srinageswari.programmedhousehold.dto.recipeitem.RecipeItemResponseDTO;
import srinageswari.programmedhousehold.model.*;
import srinageswari.programmedhousehold.repository.ItemRepository;
import srinageswari.programmedhousehold.repository.ItemtypeRepository;
import srinageswari.programmedhousehold.repository.RecipeRepository;
import srinageswari.programmedhousehold.repository.ScheduleRepository;
import srinageswari.programmedhousehold.service.appuser.AppUserServiceImpl;

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
  private final ScheduleRepository scheduleRepository;
  private final ItemtypeRepository itemtypeRepository;

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
                    recipe.getRecipeItems().stream().map(RecipeItemResponseDTO::new).toList()))
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
    final SearchSpecification<RecipeEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<RecipeResponseDTO> recipes =
        recipeRepository
            .findAll(specification, pageable)
            .map(
                recipe ->
                    new RecipeResponseDTO(
                        recipe,
                        recipe.getRecipeItems().stream().map(RecipeItemResponseDTO::new).toList()));
    if (recipes.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return recipes;
  }

  /**
   * Creates a new recipe and items belonging to the recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CommandResponseDTO create(RecipeRequestDTO request) {
    final RecipeEntity recipeEntity = recipeMapper.toEntity(request);
    recipeEntity.getRecipeItems().clear();
    request
        .getRecipeItemRequests()
        .forEach(
            recipeItemRequestDTO -> {
              final ItemEntity itemEntity;
              if (recipeItemRequestDTO.getItemId() != 0) {
                itemEntity =
                    itemRepository
                        .findById(recipeItemRequestDTO.getItemId())
                        .orElseThrow(
                            () -> {
                              log.error(Constants.NOT_FOUND_ITEM);
                              return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                            });
              } else {
                // check if the new item is already defined before
                if (itemRepository.existsByNameIgnoreCase(recipeItemRequestDTO.getItemName())) {
                  log.error(
                      String.format(
                          Constants.ALREADY_EXISTS_ITEM, recipeItemRequestDTO.getItemId()));
                  throw new ElementAlreadyExistsException(
                      String.format(
                          Constants.ALREADY_EXISTS_ITEM, recipeItemRequestDTO.getItemId()));
                }
                ItemtypeEntity itemtype =
                    itemtypeRepository.findByType(recipeItemRequestDTO.getType());
                if (null == itemtype) {
                  itemtype =
                      itemtypeRepository.save(
                          new ItemtypeEntity(0L, recipeItemRequestDTO.getType()));
                }
                itemEntity =
                    itemRepository.save(
                        new ItemEntity(0L, recipeItemRequestDTO.getItemName(), itemtype));
              }
              recipeEntity.addRecipeItem(
                  new RecipeItemEntity(
                      recipeEntity,
                      itemEntity,
                      recipeItemRequestDTO.getUnit(),
                      recipeItemRequestDTO.getItemQty()));
            });
    recipeEntity.setAppUser(appUserServiceImpl.getCurrentLoggedInUser());
    recipeRepository.save(recipeEntity);
    if (recipeEntity.isActive()) {
      ScheduleEntity scheduleEntity = new ScheduleEntity(recipeEntity);
      scheduleRepository.save(scheduleEntity);
    }
    return CommandResponseDTO.builder().id(recipeEntity.getId()).build();
  }

  /**
   * Updates recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing items for a current recipe, use RecipeItemService methods
  public CommandResponseDTO update(RecipeRequestDTO request) {
    final RecipeEntity recipeEntity =
        recipeRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeEntity.setTitle(capitalizeFully(request.getTitle()));
    recipeEntity.setDescription(request.getDescription());
    recipeEntity.setPrepTime(request.getPrepTime());
    recipeEntity.setCookTime(request.getCookTime());
    recipeEntity.setServings(request.getServings());
    recipeEntity.setInstructions(request.getInstructions());
    recipeEntity.setHealthLabel(request.getHealthLabel());
    recipeRepository.save(recipeEntity);
    return CommandResponseDTO.builder().id(recipeEntity.getId()).build();
  }

  /**
   * Deletes recipe by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final RecipeEntity recipeEntity =
        recipeRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeRepository.delete(recipeEntity);
  }

  public List<RecipeResponseDTO> getRecipeByCategoryId(Long id) {
    return recipeRepository.findrecipesByCategoryId(id).stream()
        .map(
            recipe ->
                new RecipeResponseDTO(
                    recipe,
                    recipe.getRecipeItems().stream().map(RecipeItemResponseDTO::new).toList()))
        .toList();
  }
}
