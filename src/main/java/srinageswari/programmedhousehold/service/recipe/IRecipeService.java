package srinageswari.programmedhousehold.service.recipe;

import java.util.List;
import org.springframework.data.domain.Page;
import srinageswari.programmedhousehold.common.dto.CommandResponseDTO;
import srinageswari.programmedhousehold.common.dto.SearchRequestDTO;
import srinageswari.programmedhousehold.dto.recipe.RecipeRequestDTO;
import srinageswari.programmedhousehold.dto.recipe.RecipeResponseDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeService {
  public RecipeResponseDTO findById(Long id);

  public Page<RecipeResponseDTO> findAll(SearchRequestDTO request);

  public CommandResponseDTO create(RecipeRequestDTO request);

  public CommandResponseDTO update(RecipeRequestDTO request);

  List<RecipeResponseDTO> getRecipeByCategoryId(Long id);

  public void deleteById(Long id);
}
