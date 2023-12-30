package srinageswari.programmedhousehold.dto.itemtype;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Itemtype request
 */
@Data
@NoArgsConstructor
public class ItemtypeRequestDTO {
  private Long typeId;

  @NotNull private String type;
}
