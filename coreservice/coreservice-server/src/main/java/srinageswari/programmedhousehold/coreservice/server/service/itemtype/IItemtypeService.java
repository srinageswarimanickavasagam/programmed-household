package srinageswari.programmedhousehold.coreservice.server.service.itemtype;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.client.dto.ItemtypeDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IItemtypeService {

  public ItemtypeDTO findById(Long id);

  public Page<ItemtypeDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(ItemtypeDTO request);

  public CommandResponseDTO update(ItemtypeDTO request);

  public void deleteById(Long id);
}
