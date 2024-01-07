package srinageswari.programmedhousehold.coreservice.server.service.item;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.client.dto.ItemDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IItemService {
  public ItemDTO findById(Long id);

  public Page<ItemDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(ItemDTO request);

  public CommandResponseDTO update(ItemDTO request);

  public void deleteById(Long id);
}
