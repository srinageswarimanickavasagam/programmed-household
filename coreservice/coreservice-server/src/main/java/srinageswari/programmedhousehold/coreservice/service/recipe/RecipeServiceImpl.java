package srinageswari.programmedhousehold.coreservice.service.recipe;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.ElementAlreadyExistsException;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.common.search.SearchSpecification;
import srinageswari.programmedhousehold.coreservice.mapper.RecipeMapper;
import srinageswari.programmedhousehold.coreservice.service.appuser.AppUserServiceImpl;
import srinageswari.programmedhousehold.coreservice.model.ItemEntity;
import srinageswari.programmedhousehold.coreservice.model.ItemtypeEntity;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;
import srinageswari.programmedhousehold.coreservice.model.RecipeItemEntity;
import srinageswari.programmedhousehold.coreservice.repository.ItemRepository;
import srinageswari.programmedhousehold.coreservice.repository.ItemtypeRepository;
import srinageswari.programmedhousehold.coreservice.repository.RecipeRepository;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

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
  private final ItemtypeRepository itemtypeRepository;

  /**
   * Fetches a recipe by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public RecipeDTO findById(Long id) {
    return recipeRepository
        .findById(id)
        .map(recipeMapper::toDto)
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
  public Page<RecipeDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<RecipeEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<RecipeDTO> recipes =
        recipeRepository.findAll(specification, pageable).map(recipeMapper::toDto);
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
  public CommandResponseDTO create(RecipeDTO request) {
    final RecipeEntity recipeEntity = recipeMapper.toEntity(request);
    recipeEntity.getRecipeItems().clear();
    request
        .getRecipeItemDTOS()
        .forEach(
            recipeItemRequestDTO -> {
              final ItemEntity itemEntity;
              if (recipeItemRequestDTO.getItem().getId() != 0) {
                itemEntity =
                    itemRepository
                        .findById(recipeItemRequestDTO.getItem().getId())
                        .orElseThrow(
                            () -> {
                              log.error(Constants.NOT_FOUND_ITEM);
                              return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                            });
              } else {
                // check if the new item is already defined before
                if (itemRepository.existsByNameIgnoreCase(
                    recipeItemRequestDTO.getItem().getName())) {
                  log.error(
                      String.format(
                          Constants.ALREADY_EXISTS_ITEM, recipeItemRequestDTO.getItem().getId()));
                  throw new ElementAlreadyExistsException(
                      String.format(
                          Constants.ALREADY_EXISTS_ITEM, recipeItemRequestDTO.getItem().getId()));
                }
                ItemtypeEntity itemtype =
                    itemtypeRepository.findByType(
                        recipeItemRequestDTO.getItem().getItemtype().getType());
                if (null == itemtype) {
                  itemtype =
                      itemtypeRepository.save(
                          new ItemtypeEntity(
                              0L, recipeItemRequestDTO.getItem().getItemtype().getType()));
                }
                itemEntity =
                    itemRepository.save(
                        new ItemEntity(0L, recipeItemRequestDTO.getItem().getName(), itemtype));
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
  public CommandResponseDTO update(RecipeDTO request) {
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

  public List<RecipeDTO> getRecipeByCategoryId(Long id) {
    return recipeRepository.findrecipesByCategoryId(id).stream().map(recipeMapper::toDto).toList();
  }
}
