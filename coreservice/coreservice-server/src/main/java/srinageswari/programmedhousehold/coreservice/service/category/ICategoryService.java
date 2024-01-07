package srinageswari.programmedhousehold.coreservice.service.category;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.dto.CategoryDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface ICategoryService {

  public CategoryDTO findById(Long id);

  public Page<CategoryDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(CategoryDTO request);

  public CommandResponseDTO update(CategoryDTO request);

  public void deleteById(Long id);
}
