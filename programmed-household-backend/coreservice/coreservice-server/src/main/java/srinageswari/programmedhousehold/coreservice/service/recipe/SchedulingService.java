package srinageswari.programmedhousehold.coreservice.service.recipe;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.coreservice.common.RecipeUtil;
import srinageswari.programmedhousehold.coreservice.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.coreservice.repository.RecipeRepository;

@Slf4j(topic = "SchedulingService")
@Service
@RequiredArgsConstructor
public class SchedulingService {
  private final RecipeRepository recipeRepository;

  public void resetAllRecipeSchedules(LocalDate startDt) {
    recipeRepository.resetAllRecipeSchedules(startDt);
  }

  // TODO: Implement cron job to run everyday to reschedule the previous day recipes
  public void synchronizeRecipeCalendar() {
    recipeRepository.synchronizeRecipeCalendar();
  }

  public List<RecipeResponseDTO> getTodaysRecipes() {
    return recipeRepository.queryTodaysRecipes().stream()
        .map(RecipeUtil::getRecipeResponseDTO)
        .toList();
  }

  public List<RecipeResponseDTO> getCurrentWeekRecipes() {
    LocalDate startDt = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDate endDt = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    return recipeRepository.findRecipesBetweenDates(startDt, endDt).stream()
        .map(RecipeUtil::getRecipeResponseDTO)
        .toList();
  }

  public List<RecipeResponseDTO> findRecipesBetweenDates(LocalDate startDt, LocalDate endDt) {
    return recipeRepository.findRecipesBetweenDates(startDt, endDt).stream()
        .map(RecipeUtil::getRecipeResponseDTO)
        .toList();
  }

  public List<RecipeResponseDTO> getRecipesOnSelectedDates(List<LocalDate> selectedDates) {
    return recipeRepository.getRecipesOnSelectedDates(selectedDates).stream()
        .map(RecipeUtil::getRecipeResponseDTO)
        .toList();
  }
}
