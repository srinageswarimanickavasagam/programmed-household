package srinageswari.programmedhousehold.coreservice.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.common.search.SearchSpecification;
import srinageswari.programmedhousehold.coreservice.dto.category.CategoryMapper;
import srinageswari.programmedhousehold.coreservice.dto.category.CategoryRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.category.CategoryResponseDTO;
import srinageswari.programmedhousehold.coreservice.model.CategoryEntity;
import srinageswari.programmedhousehold.coreservice.repository.CategoryRepository;

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
    final SearchSpecification<CategoryEntity> specification = new SearchSpecification<>(request);
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
    final CategoryEntity categoryEntity = categoryMapper.toEntity(request);
    categoryRepository.save(categoryEntity);
    return CommandResponseDTO.builder().id(categoryEntity.getCategoryId()).build();
  }

  /**
   * Updates category using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CommandResponseDTO update(CategoryRequestDTO request) {
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
    categoryRepository.save(categoryEntity);
    return CommandResponseDTO.builder().id(categoryEntity.getCategoryId()).build();
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
