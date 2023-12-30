package srinageswari.programmedhousehold.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.common.enums.Day;
import srinageswari.programmedhousehold.common.enums.Difficulty;
import srinageswari.programmedhousehold.common.enums.Meal;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Category request
 */
@Data
@NoArgsConstructor
public class CategoryRequestDTO {

  private Long id;

  @NotBlank private String name;

  @NotBlank private Meal meal;

  private boolean sidedish;

  private Day day;

  private Difficulty difficulty;

  private boolean isActive;
}
