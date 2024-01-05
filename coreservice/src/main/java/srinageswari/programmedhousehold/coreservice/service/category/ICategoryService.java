package srinageswari.programmedhousehold.coreservice.service.category;

import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.category.CategoryRequestDTO;
import srinageswari.programmedhousehold.coreservice.dto.category.CategoryResponseDTO;

/**
 * @author smanickavasagam
 */
public interface ICategoryService {

  public CategoryResponseDTO findById(Long id);

  public Page<CategoryResponseDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(CategoryRequestDTO request);

  public CommandResponseDTO update(CategoryRequestDTO request);

  public void deleteById(Long id);
}
