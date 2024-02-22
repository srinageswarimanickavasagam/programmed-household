package srinageswari.programmedhousehold.backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.backend.common.Constants;
import srinageswari.programmedhousehold.backend.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.backend.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.backend.service.recipe.SchedulingService;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SchedulingController {
  private final SchedulingService schedulingService;

  @PostMapping("/recipe/scheduleAll/{startDt}")
  public ResponseEntity<APIResponseDTO<Void>> scheduleAll(@PathVariable LocalDate startDt) {
    schedulingService.resetAllRecipeSchedules(startDt);
    return ResponseEntity.ok(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, null, 0));
  }

  @PostMapping("/recipe/synchronizeRecipeCalendar")
  public ResponseEntity<APIResponseDTO<Void>> synchronizeRecipeCalendar() {
    schedulingService.synchronizeRecipeCalendar();
    return ResponseEntity.ok(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, null, 0));
  }

  @GetMapping("/recipes/today")
  public ResponseEntity<APIResponseDTO<List<RecipeResponseDTO>>> getTodaysRecipes() {
    final List<RecipeResponseDTO> response = schedulingService.getTodaysRecipes();
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }

  @GetMapping("/recipes/date-range")
  public ResponseEntity<APIResponseDTO<List<RecipeResponseDTO>>> getRecipesBetweenDates(
      @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
    final List<RecipeResponseDTO> response =
        schedulingService.findRecipesBetweenDates(startDate, endDate);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }

  @GetMapping("/recipes/current-week")
  public ResponseEntity<APIResponseDTO<List<RecipeResponseDTO>>> getCurrentWeekRecipes() {
    final List<RecipeResponseDTO> response = schedulingService.getCurrentWeekRecipes();
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }

  @GetMapping("/recipes/selected-scheduleDates")
  public ResponseEntity<APIResponseDTO<List<RecipeResponseDTO>>> getRecipesOnSelectedDates(
      @RequestBody List<String> selectedScheduleDates) {
    List<LocalDate> selectedDates = selectedScheduleDates.stream().map(LocalDate::parse).toList();
    final List<RecipeResponseDTO> response =
        schedulingService.getRecipesOnSelectedDates(selectedDates);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }
}
