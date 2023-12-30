package srinageswari.programmedhousehold.service.itemtype;

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
import srinageswari.programmedhousehold.dto.itemtype.ItemtypeMapper;
import srinageswari.programmedhousehold.dto.itemtype.ItemtypeRequestDTO;
import srinageswari.programmedhousehold.dto.itemtype.ItemtypeResponseDTO;
import srinageswari.programmedhousehold.model.Itemtype;
import srinageswari.programmedhousehold.repository.ItemtypeRepository;

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
  public ItemtypeResponseDTO findById(Long id) {
    return itemtypeRepository
        .findById(id)
        .map(ItemtypeResponseDTO::new)
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
  public Page<ItemtypeResponseDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<Itemtype> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<ItemtypeResponseDTO> itemtypes =
        itemtypeRepository.findAll(specification, pageable).map(ItemtypeResponseDTO::new);
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
  public CommandResponseDTO create(ItemtypeRequestDTO request) {
    final Itemtype itemtype = itemtypeMapper.toEntity(request);
    itemtypeRepository.save(itemtype);
    return CommandResponseDTO.builder().id(itemtype.getTypeId()).build();
  }

  /**
   * Updates itemtype using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CommandResponseDTO update(ItemtypeRequestDTO request) {
    final Itemtype itemtype =
        itemtypeRepository
            .findById(request.getTypeId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    itemtype.setType(request.getType());
    itemtypeRepository.save(itemtype);
    return CommandResponseDTO.builder().id(itemtype.getTypeId()).build();
  }

  /**
   * Deletes itemtype by the given id
   *
   * @param id
   * @return
   */
  public void deleteById(Long id) {
    final Itemtype itemtype =
        itemtypeRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    itemtypeRepository.delete(itemtype);
  }
}
