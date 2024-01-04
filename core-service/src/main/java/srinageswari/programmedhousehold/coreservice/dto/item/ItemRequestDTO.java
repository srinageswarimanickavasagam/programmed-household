package srinageswari.programmedhousehold.coreservice.dto.item;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.coreservice.model.ItemtypeEntity;
import srinageswari.programmedhousehold.coreservice.model.Unit;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Item request
 */
@Data
@NoArgsConstructor
public class ItemRequestDTO {

  private Long id;

  @NotNull private String name;

  private int itemStockQty;

  private Unit unit;

  private Date stockedDt;

  @NotNull private ItemtypeEntity itemtype;
}
