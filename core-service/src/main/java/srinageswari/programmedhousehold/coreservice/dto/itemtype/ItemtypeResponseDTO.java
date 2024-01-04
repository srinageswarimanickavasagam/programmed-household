package srinageswari.programmedhousehold.coreservice.dto.itemtype;

import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.model.ItemtypeEntity;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Itemtype response
 */
@Data
@NoArgsConstructor
public class ItemtypeResponseDTO {

  private Long id;
  private String type;

  public ItemtypeResponseDTO(ItemtypeEntity itemtypeEntity) {
    this.id = itemtypeEntity.getTypeId();
    this.type = itemtypeEntity.getType();
  }
}
