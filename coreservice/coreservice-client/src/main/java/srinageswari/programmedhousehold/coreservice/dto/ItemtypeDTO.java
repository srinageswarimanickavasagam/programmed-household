package srinageswari.programmedhousehold.coreservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemtypeDTO {

  @JsonProperty("typeId")
  private Long typeId;

  @JsonProperty("type")
  @NotNull
  private String type;
}
