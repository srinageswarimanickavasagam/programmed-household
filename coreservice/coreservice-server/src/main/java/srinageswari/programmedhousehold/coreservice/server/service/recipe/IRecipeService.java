package srinageswari.programmedhousehold.coreservice.server.service.recipe;

import java.util.List;
import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.coreservice.client.dto.RecipeDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.CommandResponseDTO;
import srinageswari.programmedhousehold.coreservice.client.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeService {
  public RecipeDTO findById(Long id);

  public Page<RecipeDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(RecipeDTO request);

  public CommandResponseDTO update(RecipeDTO request);

  List<RecipeDTO> getRecipeByCategoryId(Long id);

  public void deleteById(Long id);
}
