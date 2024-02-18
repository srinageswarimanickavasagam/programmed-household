package srinageswari.programmedhousehold.coreservice.service.itemtype;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.common.exception.helper.NoSuchElementFoundException;
import srinageswari.programmedhousehold.coreservice.common.search.SearchSpecification;
import srinageswari.programmedhousehold.coreservice.dto.ItemtypeDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.mapper.ItemtypeMapper;
import srinageswari.programmedhousehold.coreservice.model.ItemtypeEntity;
import srinageswari.programmedhousehold.coreservice.repository.ItemtypeRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, removing, updating itemtype
 */
@Slf4j(topic = "CategoryService")
@Service
@RequiredArgsConstructor
public class ItemtypeServiceImpl implements IItemtypeService {

  private final ItemtypeRepository itemtypeRepository;
  private final ItemtypeMapper itemtypeMapper;

  /**
   * Fetches a itemtype by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public ItemtypeDTO findById(Long id) {
    return itemtypeRepository
        .findById(id)
        .map(itemtypeMapper::toDto)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_RECIPE);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
            });
  }

  /**
   * Fetches all itemtypes based on the given itemtype filter parameters
   *
   * @param request
   * @return Paginated itemtype data
   */
  @Transactional(readOnly = true)
  public Page<ItemtypeDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<ItemtypeEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<ItemtypeDTO> itemtypes =
        itemtypeRepository.findAll(specification, pageable).map(itemtypeMapper::toDto);
    if (itemtypes.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return itemtypes;
  }

  /**
   * Creates a new itemtype using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public ItemtypeDTO create(ItemtypeDTO request) {
    final ItemtypeEntity itemtypeEntity = itemtypeMapper.toEntity(request);
    return itemtypeMapper.toDto(itemtypeRepository.save(itemtypeEntity));
  }

  /**
   * Updates itemtype using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public ItemtypeDTO update(ItemtypeDTO request) {
    final ItemtypeEntity itemtypeEntity =
        itemtypeRepository
            .findById(request.getTypeId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    itemtypeEntity.setType(request.getType());
    return itemtypeMapper.toDto(itemtypeRepository.save(itemtypeEntity));
  }

  /**
   * Deletes itemtype by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final ItemtypeEntity itemtypeEntity =
        itemtypeRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    itemtypeRepository.delete(itemtypeEntity);
  }
}
