package srinageswari.programmedhousehold.coreservice.service.recipe;

import java.util.List;
import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeService {
  public RecipeDTO findById(Long id);

  public Page<RecipeDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(RecipeDTO request);

  public CommandResponseDTO bulkInsert(List<RecipeDTO> recipeDTOList);

  public CommandResponseDTO update(RecipeDTO request);

  List<RecipeDTO> getRecipeByCategoryId(Long id);

  public void deleteById(Long id);
}
