package srinageswari.programmedhousehold.coreservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppUserDTO {

  @JsonProperty("userId")
  private Long id;

  @JsonProperty("email")
  @NotNull
  private String email;

  @JsonProperty("name")
  private String name;

  @JsonProperty("recipes")
  private List<RecipeDTO> recipes;

  @JsonProperty("provider")
  @NotNull
  private String provider;

  @JsonProperty("createdAt")
  private LocalDateTime createdAt;
}
