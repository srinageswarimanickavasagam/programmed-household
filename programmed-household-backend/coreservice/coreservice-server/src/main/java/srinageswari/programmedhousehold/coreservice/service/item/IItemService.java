package srinageswari.programmedhousehold.coreservice.service.item;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.dto.ItemDTO;
import srinageswari.programmedhousehold.coreservice.dto.PerishableItemsResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IItemService {
  public ItemDTO findById(Long id);

  public Page<ItemDTO> findAll(SearchRequestDTO request);

  public ItemDTO create(ItemDTO request);

  public ItemDTO update(ItemDTO request);

  public void deleteById(Long id);

  public PerishableItemsResponseDTO getPerishableItems();
}
