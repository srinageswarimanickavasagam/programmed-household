package srinageswari.programmedhousehold.coreservice.service.recipe;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.coreservice.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.coreservice.repository.RecipeRepository;

@Slf4j(topic = "SchedulingService")
@Service
@RequiredArgsConstructor
public class SchedulingService {
  private final RecipeRepository recipeRepository;
  private final RecipeServiceImpl recipeServiceImpl;

  public void resetAllRecipeSchedules(Date startDt) {
    recipeRepository.resetAllRecipeSchedules(startDt);
  }

  // TODO: Implement cron job to run everyday to reschedule the previous day recipes
  public void updateSubsequentSchedules() {
    recipeRepository.updateSubsequentSchedules();
  }

  public List<RecipeResponseDTO> getTodaysRecipes() {
    return recipeRepository.queryTodaysRecipes().stream()
        .map(recipeServiceImpl::getRecipeResponseDTO)
        .toList();
  }
}
