package srinageswari.programmedhousehold.dto.item;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.common.enums.Unit;
import srinageswari.programmedhousehold.model.Itemtype;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Item request
 */
@Data
@NoArgsConstructor
public class ItemRequestDTO {

  private Long id;

  @NotNull private String name;

  private int stockQty;

  private Unit unit;

  private Date stockedDt;

  @NotNull private Itemtype itemtype;
}
