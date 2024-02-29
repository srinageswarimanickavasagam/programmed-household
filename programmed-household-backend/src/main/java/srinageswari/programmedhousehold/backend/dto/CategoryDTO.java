package srinageswari.programmedhousehold.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.backend.enums.Day;
import srinageswari.programmedhousehold.backend.enums.Difficulty;
import srinageswari.programmedhousehold.backend.enums.Meal;

@Data
@NoArgsConstructor
public class CategoryDTO {

  @JsonProperty("categoryId")
  private Long categoryId;

  @NotBlank
  @JsonProperty("name")
  private String name;

  @JsonProperty("meal")
  @NotBlank
  private Meal meal;

  @JsonProperty("sidedish")
  private boolean sidedish;

  @JsonProperty("day")
  private Day day;

  @JsonProperty("difficulty")
  private Difficulty difficulty;
}
