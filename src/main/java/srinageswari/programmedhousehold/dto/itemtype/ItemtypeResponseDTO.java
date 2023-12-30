package srinageswari.programmedhousehold.dto.itemtype;

import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.model.Itemtype;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Itemtype response
 */
@Data
@NoArgsConstructor
public class ItemtypeResponseDTO {

  private Long id;
  private String type;

  public ItemtypeResponseDTO(Itemtype itemtype) {
    this.id = itemtype.getTypeId();
    this.type = itemtype.getType();
  }
}
