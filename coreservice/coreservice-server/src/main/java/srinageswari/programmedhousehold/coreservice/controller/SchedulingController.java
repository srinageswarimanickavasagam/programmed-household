package srinageswari.programmedhousehold.coreservice.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import srinageswari.programmedhousehold.coreservice.common.Constants;
import srinageswari.programmedhousehold.coreservice.dto.RecipeResponseDTO;
import srinageswari.programmedhousehold.coreservice.dto.common.APIResponseDTO;
import srinageswari.programmedhousehold.coreservice.service.recipe.SchedulingService;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SchedulingController {
  private final SchedulingService schedulingService;

  @PostMapping("/recipe/scheduleAll/{startDt}")
  public ResponseEntity<APIResponseDTO<Void>> scheduleAll(@PathVariable Date startDt) {
    schedulingService.resetAllRecipeSchedules(startDt);
    return ResponseEntity.ok(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, null, 0));
  }

  @PostMapping("/recipe/updateSubsequentSchedules")
  public ResponseEntity<APIResponseDTO<Void>> updateSubsequentSchedules() {
    schedulingService.updateSubsequentSchedules();
    return ResponseEntity.ok(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, null, 0));
  }

  @GetMapping("/recipes/today")
  public ResponseEntity<APIResponseDTO<List<RecipeResponseDTO>>> getTodaysRecipes() {
    final List<RecipeResponseDTO> response = schedulingService.getTodaysRecipes();
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }
}
