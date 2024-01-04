package srinageswari.programmedhousehold.coreservice.service.item;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.item.ItemRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.item.ItemResponseDTO;

/**
 * @author smanickavasagam
 */
public interface IItemService {
  public ItemResponseDTO findById(Long id);

  public Page<ItemResponseDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(ItemRequestDTO request);

  public CommandResponseDTO update(ItemRequestDTO request);

  public void deleteById(Long id);
}
