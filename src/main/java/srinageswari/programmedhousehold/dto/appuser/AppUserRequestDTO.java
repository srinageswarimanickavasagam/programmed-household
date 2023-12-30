package srinageswari.programmedhousehold.dto.appuser;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author smanickavasagam
 */
@Data
@NoArgsConstructor
public class AppUserRequestDTO {
  private Long id;

  @NotNull private String email;

  private String name;

  @NotNull private String provider;
}
