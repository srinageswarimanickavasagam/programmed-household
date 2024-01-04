package srinageswari.programmedhousehold.coreservice.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.model.Day;
import srinageswari.programmedhousehold.coreservice.model.Difficulty;
import srinageswari.programmedhousehold.coreservice.model.Meal;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Category request
 */
@Data
@NoArgsConstructor
public class CategoryRequestDTO {

  private Long categoryId;

  @NotBlank private String name;

  @NotBlank private Meal meal;

  private boolean sidedish;

  private Day day;

  private Difficulty difficulty;
}
