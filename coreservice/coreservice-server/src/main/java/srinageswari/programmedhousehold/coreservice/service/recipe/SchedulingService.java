package srinageswari.programmedhousehold.coreservice.service.recipe;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.coreservice.model.RecipeEntity;
import srinageswari.programmedhousehold.coreservice.repository.RecipeRepository;

@Slf4j(topic = "SchedulingService")
@Service
@RequiredArgsConstructor
public class SchedulingService {
  private final RecipeRepository recipeRepository;

  public void scheduleAllRecipes(Date startDt) {
    recipeRepository.scheduleAllRecipes(startDt);
  }

  public void updateSubsequentSchedules() {
    List<RecipeEntity> recipeEntityList = recipeRepository.queryTodaysRecipes();
    recipeRepository.updateSubsequentSchedules(
        recipeEntityList.stream().map(RecipeEntity::getId).toList());
  }
}
