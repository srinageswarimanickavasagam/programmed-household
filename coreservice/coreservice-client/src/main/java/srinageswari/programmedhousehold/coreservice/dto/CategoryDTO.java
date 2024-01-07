package srinageswari.programmedhousehold.coreservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.enums.Day;
import srinageswari.programmedhousehold.coreservice.enums.Meal;
import srinageswari.programmedhousehold.coreservice.enums.Difficulty;

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
