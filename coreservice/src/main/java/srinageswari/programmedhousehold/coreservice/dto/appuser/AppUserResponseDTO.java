package srinageswari.programmedhousehold.coreservice.dto.appuser;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import srinageswari.programmedhousehold.coreservice.dto.recipe.RecipeResponseDTO;

/**
 * @author smanickavasagam
 */
@Data
public class AppUserResponseDTO {
  private String username;
  private String password;
  private String email;
  private String name;
  private String imageUrl;
  private List<RecipeResponseDTO> recipes;
  private String provider;
  private LocalDateTime createdAt;

  public AppUserResponseDTO(
      String username,
      String password,
      String email,
      String name,
      String imageUrl,
      List<RecipeResponseDTO> recipes,
      String provider,
      LocalDateTime createdAt) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.imageUrl = imageUrl;
    this.recipes = recipes;
    this.provider = provider;
    this.createdAt = createdAt;
  }
}
