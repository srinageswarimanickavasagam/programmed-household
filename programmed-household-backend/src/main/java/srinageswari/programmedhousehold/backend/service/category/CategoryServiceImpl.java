package srinageswari.programmedhousehold.backend.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.backend.common.Constants;
import srinageswari.programmedhousehold.backend.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.backend.common.search.SearchSpecification;
import srinageswari.programmedhousehold.backend.dto.CategoryDTO;
import srinageswari.programmedhousehold.backend.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.backend.mapper.CategoryMapper;
import srinageswari.programmedhousehold.backend.model.CategoryEntity;
import srinageswari.programmedhousehold.backend.repository.CategoryRepository;

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
  public CategoryDTO findById(Long id) {
    return categoryRepository
        .findById(id)
        .map(categoryMapper::toDto)
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
  public Page<CategoryDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<CategoryEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<CategoryDTO> categories =
        categoryRepository.findAll(specification, pageable).map(categoryMapper::toDto);
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
  public CategoryDTO create(CategoryDTO request) {
    final CategoryEntity categoryEntity = categoryMapper.toEntity(request);
    return categoryMapper.toDto(categoryRepository.save(categoryEntity));
  }

  /**
   * Updates category using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CategoryDTO update(CategoryDTO request) {
    final CategoryEntity categoryEntity =
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    categoryEntity.setName(request.getName());
    categoryEntity.setMeal(request.getMeal());
    categoryEntity.setDay(request.getDay());
    categoryEntity.setDifficulty(request.getDifficulty());
    categoryEntity.setSidedish(request.isSidedish());
    return categoryMapper.toDto(categoryRepository.save(categoryEntity));
  }

  /**
   * Deletes category by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final CategoryEntity categoryEntity =
        categoryRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    categoryRepository.delete(categoryEntity);
  }
}
