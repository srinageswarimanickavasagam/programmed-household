package srinageswari.programmedhousehold.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.common.Constants;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.common.search.SearchSpecification;
import srinageswari.programmedhousehold.dto.category.CategoryMapper;
import srinageswari.programmedhousehold.dto.category.CategoryRequestDTO;
import srinageswari.programmedhousehold.dto.category.CategoryResponseDTO;
import srinageswari.programmedhousehold.model.Category;
import srinageswari.programmedhousehold.repository.CategoryRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, removing, updating category
 */
@Slf4j(topic = "CategoryService")
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  /**
   * Fetches a category by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public CategoryResponseDTO findById(Long id) {
    return categoryRepository
        .findById(id)
        .map(CategoryResponseDTO::new)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_RECIPE);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
            });
  }

  /**
   * Fetches all categories based on the given category filter parameters
   *
   * @param request
   * @return Paginated category data
   */
  @Transactional(readOnly = true)
  public Page<CategoryResponseDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<Category> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<CategoryResponseDTO> categories =
        categoryRepository.findAll(specification, pageable).map(CategoryResponseDTO::new);
    if (categories.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return categories;
  }

  /**
   * Creates a new category using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CommandResponseDTO create(CategoryRequestDTO request) {
    final Category category = categoryMapper.toEntity(request);
    categoryRepository.save(category);
    return CommandResponseDTO.builder().id(category.getCategoryId()).build();
  }

  /**
   * Updates category using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing ingredients for a current recipe, use RecipeIngredientService methods
  public CommandResponseDTO update(CategoryRequestDTO request) {
    final Category category =
        categoryRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    category.setName(request.getName());
    category.setMeal(request.getMeal());
    category.setDay(request.getDay());
    category.setDifficulty(request.getDifficulty());
    category.setSidedish(request.isSidedish());
    categoryRepository.save(category);
    return CommandResponseDTO.builder().id(category.getCategoryId()).build();
  }

  /**
   * Deletes category by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    categoryRepository.delete(category);
  }
}
