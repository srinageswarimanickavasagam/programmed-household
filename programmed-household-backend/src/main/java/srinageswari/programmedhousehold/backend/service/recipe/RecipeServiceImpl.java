package srinageswari.programmedhousehold.backend.service.recipe;

import static org.apache.commons.text.WordUtils.capitalizeFully;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.backend.common.Constants;
import srinageswari.programmedhousehold.backend.common.RecipeUtil;
import srinageswari.programmedhousehold.backend.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.backend.common.search.SearchSpecification;
import srinageswari.programmedhousehold.backend.dto.RecipeDTO;
import srinageswari.programmedhousehold.backend.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.backend.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.backend.mapper.RecipeMapper;
import srinageswari.programmedhousehold.backend.model.*;
import srinageswari.programmedhousehold.backend.repository.RecipeRepository;
import srinageswari.programmedhousehold.backend.service.appuser.AppUserServiceImpl;
import srinageswari.programmedhousehold.backend.service.recipeitem.RecipeItemServiceImpl;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, updating, removing and fetching recipes
 */
@Slf4j(topic = "RecipeServiceImpl")
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeMapper recipeMapper;
  private final AppUserServiceImpl appUserServiceImpl;
  private final RecipeItemServiceImpl recipeItemServiceImpl;

  @Value("${app.security.enabled}")
  private boolean isSecurityEnabled;

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
        .map(RecipeUtil::getRecipeResponseDTO)
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
        recipeRepository.findAll(specification, pageable).map(RecipeUtil::getRecipeResponseDTO);
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
  public RecipeResponseDTO create(RecipeDTO request) {
      return RecipeUtil.getRecipeResponseDTO(recipeRepository.save(constructRecipeEntity(request)));
  }

  @Transactional
  public List<RecipeResponseDTO> bulkInsert(List<RecipeDTO> recipeDTOList) {
    List<RecipeEntity> entities = recipeDTOList.stream().map(this::constructRecipeEntity).toList();
    List<RecipeEntity> response = recipeRepository.saveAll(entities);
    return response.stream().map(RecipeUtil::getRecipeResponseDTO).toList();
  }

  /**
   * Updates recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing items for a current recipe, use RecipeItemService methods
  public RecipeResponseDTO update(RecipeDTO request) {
    final RecipeEntity recipeEntity =
        recipeRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeEntity.setTitle(capitalizeFully(request.getTitle()));
    recipeEntity.setReference(request.getReference());
    recipeEntity.setPrepTime(request.getPrepTime());
    recipeEntity.setCookTime(request.getCookTime());
    recipeEntity.setServings(request.getServings());
    recipeEntity.setInstructions(request.getInstructions());
    recipeEntity.setHealthLabel(request.getHealthLabel());
    return RecipeUtil.getRecipeResponseDTO(recipeRepository.save(recipeEntity));
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
    return recipeRepository.findRecipesByCategoryId(id).stream()
        .map(RecipeUtil::getRecipeResponseDTO)
        .toList();
  }

  public RecipeEntity constructRecipeEntity(RecipeDTO request) {
    final RecipeEntity recipeEntity = recipeMapper.toEntity(request);
    recipeEntity.getRecipeItems().clear();
    request
        .getRecipeItems()
        .forEach(
            recipeItemDTO ->
                recipeEntity.addRecipeItem(
                    new RecipeItemEntity(
                        recipeEntity,
                        recipeItemServiceImpl.processRecipeItem(recipeItemDTO),
                        recipeItemDTO.getRequiredQty(),
                        recipeItemDTO.getCulinaryStep())));
    recipeEntity.setAppUser(
        isSecurityEnabled ? appUserServiceImpl.getCurrentLoggedInUser() : new AppUserEntity(1L));
    recipeEntity.setId(RecipeUtil.uuidToLong(UUID.randomUUID()));
    return recipeEntity;
  }
}
