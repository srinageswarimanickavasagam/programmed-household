package srinageswari.programmedhousehold.coreservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.enums.Unit;

@Data
@NoArgsConstructor
public class ItemtypeDTO {

  @JsonProperty("typeId")
  private Long typeId;

  @JsonProperty("type")
  @NotNull
  private String type;

  @JsonProperty("stockUnit")
  @NotNull
  private Unit stockUnit;

  @JsonProperty("recipeUnit")
  @NotNull
  private Unit recipeUnit;

  @JsonProperty("refill")
  private int refill;
}
